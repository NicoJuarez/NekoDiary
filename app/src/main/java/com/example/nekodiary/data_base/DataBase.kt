package com.example.nekodiary.data_base

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(
    context: Context?,
    name: String = Misc.DB_NAME,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = Misc.DB_VERSION
) : SQLiteOpenHelper(context, name, factory, version) {

    object Misc{
         const val DB_NAME = "db"
         const val DB_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(Task.Contract.CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS ${Task.Contract.TABLE_NAME}")
        onCreate(db)

    }

    class CRUD(context: Context?) {

        private val helper: DataBase = DataBase(context)
        private val database:SQLiteDatabase = helper.writableDatabase

        fun getAllTasks():Cursor {
            return database.query(Task.Contract.TABLE_NAME, null, null,
                null, null, null, null, null)
        }

        fun insert(values: ContentValues?): Long{
            return database.insert(Task.Contract.TABLE_NAME, null, values)
        }

    }


}