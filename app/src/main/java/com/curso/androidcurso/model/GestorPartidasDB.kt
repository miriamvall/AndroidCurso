package com.curso.androidcurso.model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

object GestorPartidasDB {

    private lateinit var dbHelper: SQLiteHelper

    object PartidaColumns: BaseColumns {
        const val TABLE_NAME = "partidas"
        const val COLUMN_NAME = "name"
        const val COLUMN_POINTS = "points"
        const val COLUMN_DATE = "date"
    }

    private const val SQL_CREATE_ENTRIES: String =
            "CREATE TABLE ${PartidaColumns.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${PartidaColumns.COLUMN_NAME} TEXT," +
                    "${PartidaColumns.COLUMN_POINTS} TEXT," +
                    "${PartidaColumns.COLUMN_DATE} TEXT)"


    fun initDb(context: Context) {
        println("db initialized")
        dbHelper = SQLiteHelper(context).apply {
            setCreate("CREATE TABLE ${PartidaColumns.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${PartidaColumns.COLUMN_NAME} TEXT," +
                    "${PartidaColumns.COLUMN_POINTS} TEXT," +
                    "${PartidaColumns.COLUMN_DATE} TEXT)")
        }
    }


    fun addPartida(partida: Partida){

        val db: SQLiteDatabase = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(PartidaColumns.COLUMN_NAME, partida.getName())
            put(PartidaColumns.COLUMN_POINTS, partida.getPuntuacion())
            put(PartidaColumns.COLUMN_DATE, partida.getDate())
        }

        val newRowId = db.insert(PartidaColumns.TABLE_NAME, null, values)
    }

    fun consultarPartidas() : List<Partida>{

        val db: SQLiteDatabase = dbHelper.readableDatabase
        val projection =
                arrayOf(BaseColumns._ID, PartidaColumns.COLUMN_NAME, PartidaColumns.COLUMN_POINTS, PartidaColumns.COLUMN_DATE)

        val cursor: Cursor = db.query(
                PartidaColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        )

        val partidas: MutableList<Partida> = listOf<Partida>().toMutableList()

        with(cursor) {
            while(moveToNext()) {
                val name: String = getString(getColumnIndexOrThrow(PartidaColumns.COLUMN_NAME))
                val points: String = getString(getColumnIndexOrThrow(PartidaColumns.COLUMN_POINTS))
                val date: String = getString(getColumnIndexOrThrow(PartidaColumns.COLUMN_DATE))
                val partida = Partida(name, points, date)
                partidas.add(partida)
            }
        }
        return partidas.sortedBy { it.getPointsAsTime() }
    }
}