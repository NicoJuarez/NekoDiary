package com.example.nekodiary.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.nekodiary.activities.BaseActivity
import com.example.nekodiary.data_base.DataBase
import com.example.nekodiary.user.Preferences

open class BaseFragment: Fragment() {

    protected lateinit var database: DataBase.CRUD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = DataBase.CRUD(context)
    }

    protected fun setTint(context: Context, view: ImageView, res:Int){

        val color = ContextCompat.getColor(context, res)
        val colorStateList = ColorStateList.valueOf(color)
        ImageViewCompat.setImageTintList(view, colorStateList)
    }

    protected fun getPrefs(): Preferences?{
        return context?.let {
            (it as BaseActivity).preferences
        }
    }


}