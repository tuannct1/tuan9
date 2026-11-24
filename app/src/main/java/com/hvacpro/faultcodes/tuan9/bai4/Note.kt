package com.hvacpro.faultcodes.tuan9.bai4

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Parcelize
data class Note(
    var title: String,
    var content: String,
    var date: Long,
    val id: Int = 0
) : Parcelable {
    companion object {
        @SuppressLint("ConstantLocale")
        private val dinhDang = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        fun formatDate(date: Long): String = dinhDang.format(Date(date))
    }
}