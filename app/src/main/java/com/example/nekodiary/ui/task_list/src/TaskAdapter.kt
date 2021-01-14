package com.example.nekodiary.ui.task_list.src

import android.content.Context
import android.content.res.ColorStateList
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.nekodiary.R
import com.example.nekodiary.data_base.Task

class TaskAdapter(
    context: Context?,
    c: Cursor?,
    autoRequery: Boolean,
    var listener: View.OnClickListener? = null
) :
    CursorAdapter(context, c, autoRequery) {

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {

        val view = LayoutInflater.from(context).inflate(R.layout.task_item, parent, false)

        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {

        if (view != null && cursor != null && context != null) {
            view.findViewById<TextView>(R.id.title).text = cursor.getString(
                cursor.getColumnIndex(
                    Task.FIELD_TITLE
                )
            )
            view.findViewById<TextView>(R.id.description).text = cursor.getString(
                cursor.getColumnIndex(
                    Task.FIELD_DESCRIPTION
                )
            )


            view.findViewById<ImageView>(R.id.type).setImageDrawable(
                ContextCompat.getDrawable(
                    context, when (cursor.getString(cursor.getColumnIndex(Task.FIELD_TYPE))) {
                        Task.TYPE_JOB -> R.drawable.ic_baseline_single_task_24
                        Task.TYPE_HABIT -> R.drawable.ic_baseline_usual_task_24
                        Task.TYPE_CONTINUOUS -> R.drawable.ic_baseline_long_task_24
                        else -> R.drawable.ic_baseline_single_task_24
                    }
                )
            )

            view.findViewById<ImageView>(R.id.status).apply {
                var imgSrc: Int
                var color: ColorStateList
                when (cursor.getString(cursor.getColumnIndex(Task.FIELD_STATUS))) {
                    Task.STATUS_IN_PROGRESS -> {
                        imgSrc = R.drawable.ic_pending_task_24
                        color = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.salmon_light
                            )
                        )
                    }
                    Task.STATUS_COMPLETED -> {
                        imgSrc = R.drawable.ic_baseline_successful_task_24
                        color = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.successful_color
                            )
                        )
                    }
                    Task.STATUS_FAILED -> {
                        imgSrc = R.drawable.ic_baseline_failed_task_24
                        color = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.failed_color
                            )
                        )
                    }
                    else -> {
                        imgSrc = R.drawable.ic_pending_task_24
                        color = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.salmon_light
                            )
                        )
                    }
                }
                setImageDrawable(
                    ContextCompat.getDrawable(context, imgSrc)
                )

                view.findViewById<CardView>(R.id.card).backgroundTintList = color

                setOnClickListener {
                    this@TaskAdapter.onclick(it)
                }
            }

            view.findViewById<TextView>(R.id.item_id).text =
                cursor.getString(cursor.getColumnIndex("_id"))
        }

    }

    private fun onclick(view: View) {
        listener?.onClick(view)
    }


}