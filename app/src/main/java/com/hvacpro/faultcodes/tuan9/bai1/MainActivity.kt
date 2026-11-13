package com.hvacpro.faultcodes.tuan9.bai1

import android.app.appfunctions.RegisterAppFunctionRequest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.hvacpro.faultcodes.tuan9.R
import com.hvacpro.faultcodes.tuan9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val ds = mutableListOf(
        Note("Họp nhóm", "Họp với nhóm dự án lúc 9h sáng, chuẩn bị báo cáo tiến độ tuần 8.", "12/11/2026"),
        Note("Danh sách mua sắm", "Sữa, trứng, bánh mì, rau xà lách, cà chua và trái cây.", "11/11/2026"),
        Note("Bài tập Android", "Hoàn thành bài RecyclerView với MaterialCardView và ViewBinding.", "11/11/2026"),
        Note("Lịch khám răng", "Hẹn khám lúc 14h thứ Sáu tuần sau, nhớ mang theo thẻ bảo hiểm.", "10/11/2026"),
        Note("Ý tưởng ứng dụng", "App quét file trên thẻ SD, có lọc theo loại và tạm dừng khi đang quét.", "09/11/2026"),
        Note("Sinh nhật mẹ", "Đặt bánh kem trước 2 ngày và chọn quà tặng.", "08/11/2026"),
        Note("Sách cần đọc", "Clean Code, Kotlin in Action, Designing Data-Intensive Applications.", "07/11/2026"),
        Note("Đóng tiền internet", "Hạn đóng cuối tháng, thanh toán qua ứng dụng ngân hàng.", "05/11/2026"),
        Note("Lịch tập gym", "Thứ Hai: ngực và tay. Thứ Tư: lưng và vai. Thứ Sáu: chân.", "03/11/2026")

    )
    private lateinit var adapter : Adapter
    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val note = result.data?.let {
            IntentCompat.getParcelableExtra(it, "note", Note::class.java)
        }
        if (note != null) {
            adapter.add(note)
        }
    }
    private lateinit var binding: ActivityMainBinding
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
        adapter = Adapter(ds)
        binding.recycleView.layoutManager = LinearLayoutManager(this)
        binding.recycleView.adapter = adapter
        binding.btnFilter.setOnClickListener {
            val dialog = MenuDialogFragment()
            dialog.show(supportFragmentManager, "MenuDialog")
        }
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            launcher.launch(intent)
        }
    }
}