package com.example.nekodiary.data_base

import android.content.ContentValues

class Task(
    private var title: String? = "",
    private var description: String? = "task",
    private var type: String = TYPE_JOB,
    private var status: String = STATUS_IN_PROGRESS
    ): ITask {

    companion object {

        const val TYPE_HABIT = "1"
        const val TYPE_JOB = "2"
        const val TYPE_CONTINUOUS = "3"

        const val STATUS_IN_PROGRESS = "100"
        const val STATUS_COMPLETED = "101"
        const val STATUS_FAILED = "102"

        const val TABLE_NAME: String = "tasks"

        private const val FIELD_ID: String = "_id"

        const val FIELD_TITLE: String = "title"
        const val FIELD_DESCRIPTION: String = "desc"
        const val FIELD_TYPE: String = "type"
        const val FIELD_STATUS: String = "status"

        const val CREATE_TABLE: String =
            "create table $TABLE_NAME ($FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FIELD_TITLE TEXT, $FIELD_DESCRIPTION TEXT, $FIELD_TYPE INTEGER, $FIELD_STATUS INTEGER);"

    }

    override fun getValues():ContentValues {
        val values = ContentValues()

        values.put(FIELD_TITLE, this.title)
        values.put(FIELD_DESCRIPTION, this.description)
        values.put(FIELD_TYPE, this.type)
        values.put(FIELD_STATUS, this.status)

        return values
    }

}