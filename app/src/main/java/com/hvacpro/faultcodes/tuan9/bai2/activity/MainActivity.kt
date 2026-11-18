package com.hvacpro.faultcodes.tuan9.bai2.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai2.HanVietDatabase
import com.hvacpro.faultcodes.tuan9.bai2.adapter.Adapter
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMain2Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.cancel2.setOnClickListener {
            binding.tbstart.visibility = View.VISIBLE
            binding.tbsearch.visibility = View.GONE

        }
        binding.search.setOnClickListener {
            binding.tbstart.visibility = View.GONE
            binding.tbsearch.visibility = View.VISIBLE}
        //Tạo kết nối database
        val db = Room.databaseBuilder<HanVietDatabase>(this, "HanVietDatabase")
            .createFromAsset("vt.db")
            .setDriver(AndroidSQLiteDriver())
            .build()
        //Lấy đối tượng vietTrungDao
        val vietTrungDao = db.VietTrungDao()
        //Tạo dapter
        val ds = mutableListOf<VietTrung>()
        val adapter = Adapter(ds, ::onclick)
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adapter
        //Xử lý dự kiện edit text search
        binding.editSearch.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
                val key = s.toString()
                lifecycleScope.launch {
                    val list = withContext(Dispatchers.IO){
                        vietTrungDao.search(key)
                    }
                    ds.clear()
                    ds.addAll(list)
                    adapter.notifyDataSetChanged()
                }
            }

        })
    }
    fun onclick(vietTrung: VietTrung){
        val intent = Intent(this, HanTuActivity::class.java)
        intent.putExtra("vietTrung", vietTrung)
        startActivity(intent)
    }
}