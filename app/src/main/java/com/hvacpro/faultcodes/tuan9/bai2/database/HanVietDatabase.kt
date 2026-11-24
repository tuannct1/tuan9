package com.hvacpro.faultcodes.tuan9.bai2.database

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.hvacpro.faultcodes.tuan9.bai2.dao.HanTuDao
import com.hvacpro.faultcodes.tuan9.bai2.dao.HistoryDao
import com.hvacpro.faultcodes.tuan9.bai2.dao.VietTrungDao
import com.hvacpro.faultcodes.tuan9.bai2.entity.HanTu
import com.hvacpro.faultcodes.tuan9.bai2.entity.History
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung

@Database(entities = [HanTu::class, History::class, VietTrung::class], version = 1)
abstract class HanVietDatabase : RoomDatabase() {
    abstract fun HistoryDao(): HistoryDao
    abstract fun HanTuDao(): HanTuDao
    abstract fun VietTrungDao(): VietTrungDao
}