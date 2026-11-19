package com.hvacpro.faultcodes.tuan9.bai1

import android.annotation.SuppressLint
import android.content.Intent
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
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity(), Filter {
    var list  = mutableListOf<Note>()
    private lateinit var adapter: Adapter

    private lateinit var noteDao : NoteDao
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
        //Tạo database
        val db = Room.databaseBuilder<AppDatabase>(this, "noteDatabase")
            .setDriver(AndroidSQLiteDriver())
            .build()
        noteDao = db.noteDao()
        //Tạo list thẻ
        lifecycleScope.launch {
            val data = withContext(Dispatchers.IO){
                noteDao.getAllNote()
            }
            list.clear()
            list.addAll(data)
            adapter.notifyDataSetChanged()
        }
        //Tạo adapter
        adapter = Adapter(list, ::update, ::delete)
        //Set layout cho rcv
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter
        //Tạo filter
        val dialog = MenuDialogFragment()
        binding.btnFilter.setOnClickListener {
            dialog.show(supportFragmentManager, "MenuDialog")
        }
        //Xử lý nút add
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            launcher.launch(intent)
        }
    }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val note = result.data?.let { IntentCompat.getParcelableExtra(it, "newNote", Note::class.java) }
        lifecycleScope.launch {
            val id = withContext(Dispatchers.IO) {
                if(note != null){
                    noteDao.insert(note)
                }
                else{
                    -1L
                }
            }
            if(id != -1L){
                list.add(0, note!!.copy(id = id.toInt()))
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
                if(note != null){
                    noteDao.update(note)
                }
            }
            if(note != null){
                val pos = list.indexOfFirst { it.id == note.id }
                if (pos != -1 ) {
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
                    withContext(Dispatchers.IO) { noteDao.delete(note) }
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
    override fun ok(option : Int) {
        when(option){
            0-> list.sortBy { it.date }
            1-> list.sortByDescending { it.date }
            2-> list.sortBy { it.id }
            3-> list.sortByDescending { it.id }
            4-> list.sortBy { it.title.lowercase() }
            5-> list.sortByDescending { it.title.lowercase() }
        }
        adapter.notifyDataSetChanged()
    }
}