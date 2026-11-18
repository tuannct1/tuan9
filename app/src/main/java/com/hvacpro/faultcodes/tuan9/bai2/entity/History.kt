package com.hvacpro.faultcodes.tuan9.bai2.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName ="history")
data class History(@PrimaryKey(autoGenerate = true) @ColumnInfo( name = "_id")val id: Int = 0,
                   @ColumnInfo(name = "wordid")var wordDid: Int?,
                   @ColumnInfo(name = "viewnumber", defaultValue = "0")var viewNumber: Int?,
                   @ColumnInfo(name = "language")var language: String? = "vi") {
}