package com.hvacpro.faultcodes.tuan9.bai2.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room3.Room
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai2.activity.database.HanVietDatabase
import com.hvacpro.faultcodes.tuan9.bai2.adapter.Adapter2
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung
import com.hvacpro.faultcodes.tuan9.bai2.entity.buildChineseWords
import com.hvacpro.faultcodes.tuan9.bai2.entity.buildMeanings2
import com.hvacpro.faultcodes.tuan9.bai2.entity.splitHanCharacters
import com.hvacpro.faultcodes.tuan9.bai2.fragment.HanTuFragment
import com.hvacpro.faultcodes.tuan9.databinding.ActivityHanTuBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HanTuActivity : AppCompatActivity() {
    lateinit var db : HanVietDatabase
    private lateinit var binding: ActivityHanTuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHanTuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Tạo kết nối database
        db = Room.databaseBuilder<HanVietDatabase>(this, "HanVietDatabase")
            .createFromAsset("vt.db")
            .setDriver(AndroidSQLiteDriver())
            .build()
        //Lấy đối tượng vietTrungDao

        //Xử lí sự kiện nút back
        binding.imgBack.setOnClickListener { finish() }
        //Lấy dữ liệu từ intent
        val vietTrung = IntentCompat.getParcelableExtra(intent, "vietTrung", VietTrung::class.java)
        binding.tvWord.text = vietTrung!!.word
        binding.tvHan.text = vietTrung.content?.let{ buildChineseWords(it) }
        binding.tvEx.text = vietTrung.content?.let{ buildMeanings2(it) }
        //Tách các từ đơn
        val listHanTu = splitHanCharacters( binding.tvHan.text.toString()).toMutableList()
        val adapter = Adapter2(listHanTu,::onClick)
        binding.rcv.adapter = adapter
    }
    fun onClick(text: String){
        Log.d("Tag", text)
        val hanTuDao = db.HanTuDao()
        lifecycleScope.launch {
            val a =withContext(Dispatchers.IO){
                hanTuDao.getByHan(text)
            }
            Log.d("Tag", a.toString())
            val frm = HanTuFragment.newInstance(a)
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.frmLayout, frm)
                addToBackStack(null)
                commit()
            }
        }
    }
}