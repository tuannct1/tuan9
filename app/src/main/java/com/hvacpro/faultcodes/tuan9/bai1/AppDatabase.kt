package com.hvacpro.faultcodes.tuan9.bai1

import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import com.hvacpro.faultcodes.tuan9.bai2.HanVietDatabase

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}