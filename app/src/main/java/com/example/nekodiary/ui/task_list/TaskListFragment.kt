package com.example.nekodiary.ui.task_list

import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.database.Cursor
import android.graphics.ColorFilter
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Build.VERSION.SDK
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nekodiary.R
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.data_base.Task
import com.example.nekodiary.ui.BaseFragment
import com.example.nekodiary.ui.dialog.NewTaskDialog
import com.example.nekodiary.ui.task_list.src.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : BaseFragment() {

    private lateinit var taskListViewModel: TaskListViewModel
    private var taskCount: Int = 0
    private lateinit var list: ListView
    private lateinit var nhDialog: NewTaskDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskListViewModel =
            ViewModelProvider(this).get(TaskListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_task, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        val button: FloatingActionButton = root.findViewById(R.id.fab)
        button.setOnClickListener {
//            addNewHabit()
//            updateAdapter(DataBase.CRUD(this.context).getAllTasks())
//            val dialog = AddHabitDialog()

            // first dialog
//            dialog.listener = View.OnClickListener {
            nhDialog = NewTaskDialog()

            //second dialog
            nhDialog.listener = View.OnClickListener {
                addNewHabit()
                updateAdapter()
            }

            nhDialog.show(parentFragmentManager, null)
//            }
//
//            dialog.show(parentFragmentManager, null)
        }

        val clearButton = root.findViewById<Button>(R.id.clear_button)
        clearButton.setOnClickListener {

            val builder = AlertDialog.Builder(this.context)


            builder.apply {
                setTitle("¿Seguro de limpiar lista?")
                setPositiveButton("Si") { _, _ ->
                    DataBase.CRUD(this.context).reset()
                    updateAdapter()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                setCancelable(false)
            }
            builder.create().show()

        }

        root.findViewById<TextView>(R.id.db_version_caption).text =
            getString(R.string.db_version, (DataBase.CRUD(requireContext())).version())
//            "${context?.resources?.getString(R.string.db_version)} ${(DataBase.CRUD(requireContext())).version()}"

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        list = view.findViewById(R.id.task_list)
        fillList(list)

        context?.getSharedPreferences("_default_user", Context.MODE_PRIVATE)?.let { pref ->
            taskCount = pref.getInt("task_count", 0)
        }
    }

    private fun fillList(list: ListView?) {

        list?.let {
            val mAdapter = TaskAdapter(
                this.context,
                (DataBase.CRUD(this.context)).getAllTasks(),
                false
            )

            mAdapter.listener = View.OnClickListener { viewClicked ->
                //Toast.makeText(requireContext(), "Clicked!!", Toast.LENGTH_SHORT).show()

                val popupMenu = PopupMenu(requireContext(), viewClicked)
                popupMenu.menuInflater.inflate(R.menu.item_task_popup_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { it_item ->
                    val currentView = viewClicked.parent as View
                    when (it_item.itemId) {
                        R.id.edit -> {
                            Toast.makeText(requireContext(), "EDIT", Toast.LENGTH_SHORT).show()
                        }
                        R.id.ready -> {
//                            currentView.findViewById<ImageView>(R.id.status).apply {
//
//
//                                (this.parent.parent as CardView).backgroundTintList =
//                                    ColorStateList.valueOf(
//                                        ContextCompat.getColor(
//                                            context,
//                                            R.color.successful_color
//                                        )
//                                    )
//
//                                setImageDrawable(
//                                    ContextCompat.getDrawable(
//                                        this@TaskListFragment.requireContext(),
//                                        R.drawable.ic_baseline_successful_task_24
//                                    )
//                                )
//
//                            }


                            database.update(Task().apply {
                                setStatus(Task.STATUS_COMPLETED)
                                setID(currentView.findViewById<TextView>(R.id.item_id).text.toString())
                            })
//                            Log.d("READY::", "fillList: ${currentView.findViewById<TextView>(R.id.item_id).text.toString().toInt()}")
                            updateAdapter()
                        }
                        R.id.failed -> {
                            currentView.findViewById<ImageView>(R.id.status).apply {

//                                setTint(
//                                    context, this,
//                                    R.color.failed_color
//                                )
                                (this.parent.parent as CardView).backgroundTintList =
                                    ColorStateList.valueOf(
                                        ContextCompat.getColor(
                                            context,
                                            R.color.failed_color
                                        )
                                    )
                                setImageDrawable(
                                    ContextCompat.getDrawable(
                                        this@TaskListFragment.requireContext(),
                                        R.drawable.ic_baseline_failed_task_24
                                    )
                                )

                            }
                        }
                        R.id.delete -> {
                            DataBase.CRUD(this.context).delete(
                                Integer.valueOf(
                                    currentView.findViewById<TextView>(
                                        R.id.item_id
                                    ).text.toString()
                                )
                            )
                            updateAdapter()

                        }
                    }

                    return@setOnMenuItemClickListener true

                }
                popupMenu.show()
            }

            it.adapter = mAdapter
        }

    }

    private fun addNewHabit() {
        DataBase.CRUD(this.context)
            .insert(
                nhDialog.getTask()
//                Task(
//                    "Tarea $taskCount",
//                    "Descripción de la tarea",
//                    Task.TYPE_JOB,
//                    Task.STATUS_IN_PROGRESS
//                )
            )

        taskCount++
        context?.getSharedPreferences("_default_user", Context.MODE_PRIVATE)?.edit()
            ?.putInt("task_count", taskCount)?.apply()
    }

    private fun updateAdapter() {
        //list.deferNotifyDataSetChanged()
        (list.adapter as TaskAdapter).swapCursor(DataBase.CRUD(this.context).getAllTasks())
        list.setSelection(list.adapter.count - 1)
    }
}