package com.example.nekodiary.ui.gallery.src

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.nekodiary.R
import com.example.nekodiary.data_base.Task

class TaskAdapter(context: Context?, c: Cursor?, autoRequery: Boolean) :
    CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.task, parent, false)

        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        view?.findViewById<TextView>(R.id.title)?.text = cursor?.getString(cursor.getColumnIndex(
            Task.Contract.FIELD_TITLE))


    }




}