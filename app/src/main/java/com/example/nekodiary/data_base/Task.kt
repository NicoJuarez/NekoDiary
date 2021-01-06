package com.example.nekodiary.data_base

class Task /*(description: String = "task")*/ {



    object Contract{

        const val TABLE_NAME: String = "tasks"

        private const val FIELD_ID: String = "_id"

        const val FIELD_TITLE: String = "title"
        const val FIELD_DESCRIPTION: String = "desc"
        const val FIELD_TYPE: String = "type"
        const val FIELD_STATUS: String = "status"

        const val CREATE_TABLE: String = "create table $TABLE_NAME ($FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FIELD_TITLE TEXT, $FIELD_DESCRIPTION TEXT, $FIELD_TYPE INTEGER, $FIELD_STATUS INTEGER);"

    }


//    interface Contract{
//
//        val CREATE_TABLE: String
//            get() = ""
//
//    }

}