package com.curso.androidcurso.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    var SQL_CREATE_ENTRIES: String = ""

    fun setCreate(query: String) {
        this.SQL_CREATE_ENTRIES = query
        println("SETCREATE FUCTION")
        println(this.SQL_CREATE_ENTRIES)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        println(this.SQL_CREATE_ENTRIES)
        db?.execSQL(this.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "LocalStats.db"
    }

}