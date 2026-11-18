package com.hvacpro.faultcodes.tuan9.bai2.dao

import androidx.room3.Dao
import androidx.room3.Query
import com.hvacpro.faultcodes.tuan9.bai2.entity.VietTrung

@Dao
interface VietTrungDao {
    @Query("SELECT * FROM  viet_trung WHERE search LIKE  '%' || :word || '%' LIMIT 20")
    suspend fun search(word: String): List<VietTrung>

}