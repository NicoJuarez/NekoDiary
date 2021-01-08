package com.example.nekodiary.ui.task_list

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nekodiary.R
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.data_base.Task
import com.example.nekodiary.ui.dialog.AddHabitDialog
import com.example.nekodiary.ui.dialog.NewHabitDialog
import com.example.nekodiary.ui.task_list.src.TaskAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TaskListFragment : Fragment() {

    private lateinit var taskListViewModel: TaskListViewModel
    private var taskCount: Int = 0
    private lateinit var list: ListView
    private lateinit var nhDialog: NewHabitDialog

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
            nhDialog = NewHabitDialog()

            //second dialog
            nhDialog.listener = View.OnClickListener {
                addNewHabit()
                updateAdapter(DataBase.CRUD(this.context).getAllTasks())
            }

            nhDialog.show(parentFragmentManager, null)
//            }
//
//            dialog.show(parentFragmentManager, null)
        }

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
        list?.adapter =
            TaskAdapter(this.context, (DataBase.CRUD(this.context)).getAllTasks(), false)

    }

    private fun addNewHabit() {
        DataBase.CRUD(this.context)
            .insert(
                nhDialog.task
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

    private fun updateAdapter(cursor: Cursor) {
        //list.deferNotifyDataSetChanged()
        (list.adapter as TaskAdapter).swapCursor(cursor)
        list.setSelection(list.adapter.count - 1)
    }
}