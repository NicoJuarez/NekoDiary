package com.example.nekodiary.data_base

import android.content.ContentValues

class Task() : ITask {

    private val values = ContentValues()

    constructor(title: String, description: String, type: String, status: String):this(){
        setTitle(title)
        setDescription(description)
        setType(type)
        setStatus(status)
    }

    fun setTitle(title: String){
        values.put(FIELD_TITLE, title)
    }

    fun setDescription(description: String){
        values.put(FIELD_DESCRIPTION, description)
    }

    fun setType(type: String){
        values.put(FIELD_TYPE, type)
    }

    fun setStatus(status: String){
        values.put(FIELD_STATUS, status)
    }

    fun setID (id: Int){
        setID(id.toString())
    }

    fun setID(id: String){
        values.put(FIELD_ID, id)
    }

    override fun getValues(): ContentValues {
        return values
    }

    companion object {

        const val TYPE_HABIT = "1"
        const val TYPE_JOB = "2"
        const val TYPE_CONTINUOUS = "3"

        const val STATUS_IN_PROGRESS = "100"
        const val STATUS_COMPLETED = "101"
        const val STATUS_FAILED = "102"

        const val TABLE_NAME: String = "tasks"

        const val FIELD_ID: String = "_id"

        const val FIELD_TITLE: String = "title"
        const val FIELD_DESCRIPTION: String = "desc"
        const val FIELD_TYPE: String = "type"
        const val FIELD_STATUS: String = "status"

        const val CREATE_TABLE: String =
            "create table $TABLE_NAME ($FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FIELD_TITLE TEXT, $FIELD_DESCRIPTION TEXT, $FIELD_TYPE INTEGER, $FIELD_STATUS INTEGER);"

    }


}