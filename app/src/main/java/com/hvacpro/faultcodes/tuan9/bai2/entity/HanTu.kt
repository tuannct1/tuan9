package com.hvacpro.faultcodes.tuan9.bai2.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "han_tu")
data class HanTu(@PrimaryKey(autoGenerate = true) @ColumnInfo( name = "_id")val id: Int = 0,
            var han: String,
            var viet: String,
            var pinyin: String,
            var mean: String) {
}