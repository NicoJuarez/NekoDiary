package com.example.nekodiary.user

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context){

    private val preferences: SharedPreferences = context.getSharedPreferences(user, Context.MODE_PRIVATE)

    fun setValue(key: String, value: String){
        preferences.edit().putString(key, value).apply()
    }

    fun getValue(key: String):String{
        return preferences.getString(key, NONE)!!
    }

    companion object User{
        private const val user = "_default_user"
        const val BATTERY_NOTIFICATION = "battery_notification"
        const val NONE = "_none_value"
    }
}