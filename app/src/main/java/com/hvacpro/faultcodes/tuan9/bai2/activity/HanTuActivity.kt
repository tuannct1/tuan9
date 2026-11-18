package com.hvacpro.faultcodes.tuan9.bai2.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.bai2.adapter.Adapter2
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung
import com.hvacpro.faultcodes.tuan9.bai2.entity.buildChineseWords
import com.hvacpro.faultcodes.tuan9.bai2.entity.buildMeanings
import com.hvacpro.faultcodes.tuan9.bai2.entity.splitHanCharacters
import com.hvacpro.faultcodes.tuan9.databinding.ActivityHanTuBinding

class HanTuActivity : AppCompatActivity() {
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
        val vietTrung = IntentCompat.getParcelableExtra(intent, "vietTrung", VietTrung::class.java)
        binding.tvWord.text = vietTrung!!.word
        binding.tvHan.text = vietTrung.content?.let{buildChineseWords(it)}
        binding.tvEx.text = vietTrung.content?.let{ buildMeanings(it) }
        val listHanTu = splitHanCharacters( binding.tvHan.text.toString()).toMutableList()
        val adapter = Adapter2(listHanTu,::onClick)
        binding.rcv.adapter = adapter
    }
    fun onClick(text: String){

    }
}