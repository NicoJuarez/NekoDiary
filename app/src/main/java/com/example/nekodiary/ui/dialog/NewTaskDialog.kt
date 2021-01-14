package com.example.nekodiary.ui.dialog

import android.animation.Animator
import android.app.ActionBar
import android.content.Context
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.nekodiary.R
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.data_base.Task
import com.example.nekodiary.ui.fx.ViewShaker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NewTaskDialog(var listener: View.OnClickListener? = null) : DialogFragment() {

    private lateinit var task: Task
    private lateinit var titleLayout: TextInputLayout
    private var shaking = false
    private val shakeTiming = 200L

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

        titleLayout = view.findViewById(R.id.title_input_layout)

        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        view.findViewById<ImageView>(R.id.confirm_button).setOnClickListener {
            if (view.findViewById<TextInputEditText>(R.id.title_input_edit)
                    .text.toString().isEmpty()
            ) {

                titleLayout.error = "El campo está vacío. Es requerido"
                if (vibrator.hasVibrator() && !shaking) {

                    shakeLayout(vibrator)

                    shaking = true;

                    Handler(Looper.getMainLooper()).postDelayed({
                        shaking = false
                        ViewShaker.stop()
                    }, shakeTiming + 100L)
                }

            } else {
                titleLayout.error = null

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
            } //end else
        }// end listener

        view.findViewById<ImageView>(R.id.cancel_button).setOnClickListener {
            dialog?.dismiss()
        }

    }

    override fun onStart() {
        super.onStart()
        dialog?.let {
            it.window?.apply {
                setLayout(
                    ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.WRAP_CONTENT
                )
                findViewById<RadioGroup>(R.id.radio_group).check(R.id.single)
            }
            it.setCanceledOnTouchOutside(false)
            it.window?.setBackgroundDrawableResource(android.R.color.transparent)
        }


    }

    fun getTask(): Task {
        return this.task
    }


    private fun shakeLayout(vibrator: Vibrator) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    shakeTiming,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        else
            @Suppress("DEPRECATION")
            vibrator.vibrate(shakeTiming)





        ViewShaker.shake(titleLayout)

    }


}