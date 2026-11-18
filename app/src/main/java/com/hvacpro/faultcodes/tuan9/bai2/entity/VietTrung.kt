package com.hvacpro.faultcodes.tuan9.bai2.entity

import android.os.Parcelable
import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "viet_trung",
    indices = [
        Index(value = ["search"], name= "vt_s"),
        Index(value = ["word"], name= "vt_w")
    ]
)
data class VietTrung(@PrimaryKey(autoGenerate = true) @ColumnInfo( name = "_id")val id: Int? = 0,
                     var word: String?,
                     var content: String?,
                     var search: String?
                  ): Parcelable {
}
private val junkTag = Regex("""</?[A-Za-z][^>]*>""")
private val cjk = Regex("[\\u4E00-\\u9FFF]")

private fun cleanContent(raw: String): String =
    raw.replace(junkTag, "\n")
        .replace("&nbsp;", " ")

fun buildChineseWords2(text: String): String{
    val a = text.split("☆")
    val b = a.drop(1)
    val ds = mutableListOf<String>()
    for(tt in b){
        val head = tt.substringBefore("<")       // 2. phần trước dấu <
        for (m in cjk.findAll(head)) {           // 3. tìm các cụm chữ Hán trong đó
            ds.add(m.value)                      // 4. thêm từng cụm vào danh sách
        }
    }
    return ds.joinToString("; ")
}
fun buildChineseWords(content: String, max: Int = 4, separator: String = "; "): String =
    cleanContent(content)
        .split("☆")
        .drop(1)
        .flatMap { part ->
            part.lineSequence().first()
                .substringBefore("<")
                .substringBefore("(")
                .substringBefore("（")
                .split(';', '；', ',', '，')          // tách "呀; 哑" thành từng từ
                .map { it.trim().trimEnd('。', '.', ' ') }
        }
        .filter { cjk.containsMatchIn(it) }
        .distinct()
        .take(max)
        .joinToString(separator)
fun buildMeanings2(content: String) : String{

    return content
}
fun buildMeanings(content: String, max: Int = 3, separator: String = " / "): String =
    cleanContent(content)
        .split("☆")
        .drop(1)
        .mapNotNull { part ->
            val line = part.lineSequence().first()
            if (!line.contains("<")) return@mapNotNull null
            line.substringAfter("<")
                .substringBefore(">")
                .trim()
                .trimEnd('。', ';', ',', '.', ' ')
                .takeIf { it.isNotEmpty() && cjk.containsMatchIn(it) }
        }
        .take(max)
        .joinToString(separator)
fun splitHanCharacters(text: String): List<String> {
    return text.filter { it.isLetter() && it.code in 0x4E00..0x9FFF }
        .map { it.toString() }
}
