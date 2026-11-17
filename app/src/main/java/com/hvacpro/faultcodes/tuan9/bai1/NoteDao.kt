package com.hvacpro.faultcodes.tuan9.bai1

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import androidx.room3.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note) : Long
    @Update
    suspend fun update(note: Note)
    @Delete
    suspend fun delete(note: Note)
    @Query("SELECT * FROM NOTE ORDER BY date DESC")
    suspend fun  getAllNote( ): MutableList<Note>
}