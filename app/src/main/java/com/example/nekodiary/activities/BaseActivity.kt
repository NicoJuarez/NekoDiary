package com.example.nekodiary.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.nekodiary.util.Preferences

open class BaseActivity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        preferences = Preferences(this)
    }


    override fun onStart() {
        super.onStart()
        preferences = Preferences(this)
    }
}