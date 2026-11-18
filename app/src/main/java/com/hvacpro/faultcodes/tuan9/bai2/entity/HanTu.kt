package com.hvacpro.faultcodes.tuan9.bai2.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey

@Entity(tableName = "han_tu",
        indices = [
            Index(value = ["han"], name="h_h"),
            Index(value = ["pinyin"], name="h_p"),
            Index(value = ["viet"], name="h_v"),
        ]
)
data class HanTu(@PrimaryKey(autoGenerate = true) @ColumnInfo( name = "_id")val id: Int? = 0,
            var han: String?,
            var viet: String?,
            var pinyin: String?,
            var mean: String?) {
}