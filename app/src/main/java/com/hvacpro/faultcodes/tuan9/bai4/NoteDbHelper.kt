package com.hvacpro.faultcodes.tuan9.bai4

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDbHelper(context: Context) : SQLiteOpenHelper(context, "noteDatabase", null, 1) {

    companion object {
        private const val TABLE_NAME = "NOTE"
        private const val COL_ID = "id"
        private const val COL_TITLE = "title"
        private const val COL_CONTENT = "content"
        private const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_TITLE TEXT NOT NULL,
                $COL_CONTENT TEXT NOT NULL,
                $COL_DATE INTEGER NOT NULL
            )
        """.trimIndent()
        db.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insert(note: Note): Long {
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_CONTENT, note.content)
            put(COL_DATE, note.date)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    fun update(note: Note): Int {
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_CONTENT, note.content)
            put(COL_DATE, note.date)
        }
        return writableDatabase.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(note.id.toString()))
    }

    fun delete(note: Note): Int {
        return writableDatabase.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(note.id.toString()))
    }

    fun getAllNote(): MutableList<Note> {
        val list = mutableListOf<Note>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY $COL_DATE DESC", null)
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(COL_ID))
                val title = it.getString(it.getColumnIndexOrThrow(COL_TITLE))
                val content = it.getString(it.getColumnIndexOrThrow(COL_CONTENT))
                val date = it.getLong(it.getColumnIndexOrThrow(COL_DATE))
                list.add(Note(title, content, date, id))
            }
        }
        return list
    }
}