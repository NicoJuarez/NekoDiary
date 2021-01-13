package com.example.nekodiary.ui

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    protected fun setTint(context: Context, view: ImageView, res:Int){

        val color = ContextCompat.getColor(context, res)
        val colorStateList = ColorStateList.valueOf(color)
        ImageViewCompat.setImageTintList(view, colorStateList)
    }


}