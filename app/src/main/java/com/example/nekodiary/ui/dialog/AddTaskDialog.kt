package com.example.nekodiary.ui.dialog

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.nekodiary.R
import org.w3c.dom.Text

class AddTaskDialog(var listener: View.OnClickListener? = null) : DialogFragment() {



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.add_habit_dialog, container, false)
        configureComponents(view)
        return view
    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.WRAP_CONTENT
        )
    }

    private fun configureComponents(view: View) {
        view.findViewById<Button>(R.id.add_button).setOnClickListener {
            this.listener?.onClick(it)
            dialog?.dismiss()
        }

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            dialog?.dismiss()
        }
    }




}