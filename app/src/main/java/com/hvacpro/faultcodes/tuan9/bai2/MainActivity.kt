package com.hvacpro.faultcodes.tuan9.bai2

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room3.Room
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.AndroidSQLiteDriver
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai2.entity.HanTu
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMain2Binding
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMainBinding
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
        val db = Room.databaseBuilder<HanVietDatabase>(this, "HanVietDatabase")
            .createFromAsset("vt.db")
            .setDriver(AndroidSQLiteDriver())
            .build()
        val vietTrungDao = db.VietTrungDao()
        val ds = mutableListOf<VietTrung>()
        lifecycleScope.launch {
            val a = withContext(Dispatchers.IO){
                vietTrungDao.search("a")
            }
            ds.add(a)
        }
        val adapter = Adapter(ds,::onclick)
        binding.rcv.layoutManager = LinearLayoutManager(this)
        binding.rcv.adapter = adapter
    }
    fun onclick(vietTrung: VietTrung){

    }
}