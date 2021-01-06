package com.example.nekodiary.ui.gallery

import android.content.ContentValues
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.nekodiary.R
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.data_base.Task
import com.example.nekodiary.ui.gallery.src.TaskAdapter

class GalleryFragment : Fragment() {

    private lateinit var galleryViewModel: GalleryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
                ViewModelProvider(this).get(GalleryViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_task, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list = view.findViewById<ListView>(R.id.task_list)
        fillList(list)
    }

    private fun fillList(list: ListView?){

        val crud = DataBase.CRUD(this.context)
        val values = ContentValues()

        values.put(Task.Contract.FIELD_TITLE, "Tarea 3")
        values.put(Task.Contract.FIELD_DESCRIPTION, "Tarea 3")
        values.put(Task.Contract.FIELD_TYPE, "Tarea 3")
        values.put(Task.Contract.FIELD_STATUS, "Tarea 3")

        crud.insert(values)

//        list?.adapter = TaskAdapter(this.context, (DataBase.CRUD(this.context)).getAllTasks(), false)
        list?.adapter = TaskAdapter(this.context, crud.getAllTasks(), false)

    }
}