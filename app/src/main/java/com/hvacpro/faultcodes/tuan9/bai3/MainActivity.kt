package com.hvacpro.faultcodes.tuan9.bai3

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), Filter {

    companion object {
        private const val DB_NAME = "noteDatabase"
        private const val TABLE_NAME = "NOTE"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"
        private const val COL_DATE = "date"
    }

    var list = mutableListOf<Note>()
    private lateinit var adapter: Adapter
    private lateinit var db: SQLiteDatabase
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.toolBar.title = "abc"

        // 1. Khởi tạo SQLiteDatabase trực tiếp
        initDatabase()

        // 2. Tạo adapter và gán cho RecyclerView trước khi load dữ liệu
//        adapter = Adapter(list, ::update, ::delete)
//        binding.recycleView.layoutManager = LinearLayoutManager(this)
//        binding.recycleView.adapter = adapter

        // 3. Lấy dữ liệu ban đầu
        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO) {
                getAllNote()
            }
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }

        // Tạo filter
        val dialog = MenuDialogFragment()
        binding.btnFilter.setOnClickListener {
            dialog.show(supportFragmentManager, "MenuDialog")
        }

        // Xử lý nút add
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            launcher.launch(intent)
        }
    }

    private fun initDatabase() {
        db = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        val createTable = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT NOT NULL,
                $COL_CONTENT TEXT NOT NULL,
                $COL_DATE INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    private fun insertNote(note: Note): Long {
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_CONTENT, note.content)
            put(COL_DATE, note.date)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    private fun updateNote(note: Note): Int {
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_CONTENT, note.content)
            put(COL_DATE, note.date)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(note.id.toString()))
    }

    private fun deleteNote(note: Note): Int {
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(note.id.toString()))
    }

    private fun getAllNote(): MutableList<Note> {
        val resultList = mutableListOf<Note>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_DATE DESC", null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COL_ID))
                val title = it.getString(it.getColumnIndexOrThrow(COL_TITLE))
                val content = it.getString(it.getColumnIndexOrThrow(COL_CONTENT))
                val date = it.getLong(it.getColumnIndexOrThrow(COL_DATE))
                resultList.add(Note(title, content, date, id))
            }
        }
        return resultList
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val note = result.data?.let {
            IntentCompat.getParcelableExtra(it, "newNote", Note::class.java)
        }
        lifecycleScope.launch {
            val id = withContext(Dispatchers.IO) {
                if (note != null) insertNote(note) else -1L
            }
            if (id != -1L && note != null) {
                list.add(0, note.copy(id = id.toInt()))
                adapter.notifyItemInserted(0)
                binding.recycleView.scrollToPosition(0)
            }
        }
    }

    private val updateLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val note = result.data?.let {
            IntentCompat.getParcelableExtra(it, "noteUpdate", Note::class.java)
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                if (note != null) updateNote(note)
            }
            if (note != null) {
                val pos = list.indexOfFirst { it.id == note.id }
                if (pos != -1) {
                    list[pos] = note
                    adapter.notifyItemChanged(pos)
                }
            }
        }
    }

    fun update(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java)
        intent.putExtra("note", note)
        updateLauncher.launch(intent)
    }

    fun delete(note: Note, view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.delete, popup.menu)
        popup.setOnMenuItemClickListener {
            if (it.itemId == R.id.itemDelete) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) { deleteNote(note) }
                    val pos = list.indexOfFirst { a -> a.id == note.id }
                    if (pos != -1) {
                        list.removeAt(pos)
                        adapter.notifyItemRemoved(pos)
                    }
                }
            }
            true
        }
        popup.show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun ok(option: Int) {
        when (option) {
            0 -> list.sortBy { it.date }
            1 -> list.sortByDescending { it.date }
            2 -> list.sortBy { it.id }
            3 -> list.sortByDescending { it.id }
            4 -> list.sortBy { it.title.lowercase() }
            5 -> list.sortByDescending { it.title.lowercase() }
        }
        adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::db.isInitialized && db.isOpen) {
            db.close()
        }
    }
}