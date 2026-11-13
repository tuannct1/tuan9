package com.hvacpro.faultcodes.tuan9.bai1

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.databinding.ActivityAddNoteBinding
import java.net.Proxy
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddNoteActivity : AppCompatActivity() {
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
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        binding.tvDate.text = sdf.format(calendar.time)
        binding.btnSave.setOnClickListener {
            val note = Note(binding.edtTitle.text.toString(), binding.edtContent.text.toString(), binding.tvDate.text.toString())
            val intent = Intent()
            intent.putExtra("note", note)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.edtTitle.setOnFocusChangeListener { view, boolean ->
            val edt = view as EditText
            if(boolean){
                Log.d("TAA", "asdsad")
                edt.setTypeface(null, Typeface.BOLD)
                edt.setTextColor(Color.BLACK)
            }
        }
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
