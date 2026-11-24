package com.hvacpro.faultcodes.tuan9.bai4

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    var addStatus = true
    private lateinit var binding : ActivityAddNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Tạo date dạng Long
        val date = System.currentTimeMillis()
        //Lấy note từ activity
        val oldNote = IntentCompat.getParcelableExtra(intent, "note", Note::class.java)
        //Kiểm tra note từ activity nếu khác null thì hiện lên màn hình
        if(oldNote != null){
            addStatus = false
            binding.edtTitle.setText(oldNote.title)
            binding.edtContent.setText(oldNote.content)
        }
            binding.tvDate.text = Note.formatDate(date)
        //Xử lý nút save
        binding.btnSave.setOnClickListener {
            val intent = Intent()
            if(addStatus){
                val newNote = Note(binding.edtTitle.text.toString(), binding.edtContent.text.toString(), date)
                intent.putExtra("newNote", newNote)
            }
            else{
                val note = oldNote?.copy(title = binding.edtTitle.text.toString(), content = binding.edtContent.text.toString(), date = date)
                intent.putExtra("noteUpdate", note)
            }
            setResult(RESULT_OK, intent)
            finish()
        }
        //Xử lý hiện thị khi viết title
        binding.edtTitle.setOnFocusChangeListener { view, boolean ->
            val edt = view as EditText
            if(boolean){
                edt.setTypeface(null, Typeface.BOLD)
                edt.setTextColor(Color.BLACK)
            }
        }
        //Xử lý nút back
        binding.btnBack.setOnClickListener {
            AlertDialog.Builder(this)
                .setMessage("Ban có chắc muốn rời khỏi chứ")
                .setPositiveButton("Đồng ý"){_,_->
                    finish()
                }
                .setNegativeButton("Cancel"){_,_->}
                .show()
        }

    }
}
