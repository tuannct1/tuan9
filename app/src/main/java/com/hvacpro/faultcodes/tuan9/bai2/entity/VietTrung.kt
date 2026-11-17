package com.hvacpro.faultcodes.tuan9.bai2.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "viet_trung")
data class VietTrung(@PrimaryKey(autoGenerate = true) @ColumnInfo( name = "_id")val id: Int = 0,
                     var word: String,
                     var content: String,
                     var search: String
                  ) {
}