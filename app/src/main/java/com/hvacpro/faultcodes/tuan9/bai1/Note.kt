package com.hvacpro.faultcodes.tuan9.bai1
import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room3.Entity
import androidx.room3.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Parcelize
@Entity
data class Note(
                var title: String,
                var content: String,
                var date: Long,
                @PrimaryKey(autoGenerate = true) val id: Int = 0) : Parcelable{

    companion object {
        @SuppressLint("ConstantLocale")
        private val dinhDang = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        fun formatDate(date: Long): String = dinhDang.format(Date(date))
    }

}

