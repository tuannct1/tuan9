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
private val nonCjk = Regex("[^\\u4E00-\\u9FFF]")

private fun cleanContent(text: String): String =
    text.replace("<br>", "\n")
        .replace("<y>", "\n")
        .replace(junkTag, "")
        .replace("&nbsp;", " ")
        .replace(Regex("[ \\t]+"), " ")
        .replace(Regex("\\n\\s*\\n+"), "\n")
        .trim()

fun buildChineseWords(text: String): String{
    val a = text.split("☆")
    val b = a.drop(1)
    val ds = mutableListOf<String>()
    for(tt in b){
        val x = tt.substringBefore("<").replace(nonCjk, "")
        ds.add(x)
    }
    return ds.joinToString("; ")
}
fun buildMeanings2(content: String): String{
    val cleaned = cleanContent(content)
    val a = cleaned.split("☆")
    val b = a.drop(1).map { it.trim() }
    return b.joinToString ("\n")
}
fun splitHanCharacters(text: String): List<String> {
    return text.filter { it.isLetter() && cjk.containsMatchIn(it.toString()) }
        .map { it.toString() }
}
