package com.hvacpro.faultcodes.tuan9.bai2.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.hvacpro.faultcodes.tuan9.bai2.entity.HanTu

@Dao
interface HanTuDao {
    @Query("SELECT * FROM han_tu WHERE _id = :id")
    suspend fun getByID(id: Int): HanTu
}