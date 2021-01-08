package com.example.nekodiary.ui.dialog

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.example.nekodiary.R
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.data_base.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NewHabitDialog(var listener: View.OnClickListener? = null) : DialogFragment() {

    lateinit var task: Task

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.new_habit_dialog, container, false)
        configureComponents(view)
        return view
    }

    private fun configureComponents(view: View) {

        view.findViewById<ImageView>(R.id.confirm_button).setOnClickListener {

            var a = view.findViewById<TextInputEditText>(R.id.title_input_edit).text.toString()
                .isEmpty()
            var b =
                view.findViewById<TextInputEditText>(R.id.description_input_edit).text.toString()
                    .isEmpty()
            if (a || b) {
                val layout = view.findViewById<TextInputLayout>(R.id.title_input_layout)
                if (a) {
                    layout.error = "El campo está vacío. Es requerido"
                }else{
                    layout.error = null
                }

            } else {
                this.task = Task(
                    view.findViewById<TextInputEditText>(R.id.title_input_edit).text.toString(),
                    view.findViewById<TextInputEditText>(R.id.description_input_edit).text.toString(),
                    when (view.findViewById<RadioGroup>(R.id.radio_group).checkedRadioButtonId) {
                        R.id.single -> Task.TYPE_JOB
                        R.id.usual -> Task.TYPE_HABIT
                        R.id.long_task -> Task.TYPE_CONTINUOUS
                        else -> Task.TYPE_JOB
                    },
                    Task.STATUS_IN_PROGRESS
                )
                listener?.onClick(it)
                dialog?.dismiss()
            }
        }// end listener

        view.findViewById<ImageView>(R.id.cancel_button).setOnClickListener {
            dialog?.dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.setLayout(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
            )
            it.setCanceledOnTouchOutside(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }


}