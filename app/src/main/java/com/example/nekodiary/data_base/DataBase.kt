package com.example.nekodiary.data_base

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(
    context: Context?,
    name: String = DB_NAME,
    factory: SQLiteDatabase.CursorFactory? = null,
    version: Int = DB_VERSION
) : SQLiteOpenHelper(context, name, factory, version) {

    companion object Misc {
        const val DB_NAME = "db"
        const val DB_VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(Task.CREATE_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS ${Task.TABLE_NAME}")
        onCreate(db)

    }

    class CRUD(context: Context?) {

        private val helper: DataBase = DataBase(context)
        private val database: SQLiteDatabase = helper.writableDatabase

        fun getAllTasks(): Cursor {
            return database.query(
                Task.TABLE_NAME, null, null,
                null, null, null, null, null
            )
        }

        fun insert(values: ContentValues?): Long {
            return database.insert(Task.TABLE_NAME, null, values)
        }

        fun insert(task: Task): Long{
            return this.insert(task.getValues())
        }

        fun reset(){
            val db = helper.readableDatabase
            db.execSQL("DELETE FROM ${Task.TABLE_NAME}")
            //database.execSQL()
        }

        fun version(): Int{
            return database.version
        }

        fun delete(id: Int){
            helper.readableDatabase.execSQL("DELETE FROM ${Task.TABLE_NAME} WHERE ${Task.TABLE_NAME}._id = $id")
        }

    }


}