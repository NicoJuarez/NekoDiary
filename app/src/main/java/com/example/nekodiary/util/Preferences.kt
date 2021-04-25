package com.example.nekodiary.util

import android.content.Context
import android.content.SharedPreferences
import com.example.nekodiary.ui.timer.TimerFragment

class Preferences(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(user, Context.MODE_PRIVATE)

    fun setValue(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getValue(key: String): String {
        return preferences.getString(key, NONE)!!
    }

    fun getTimerLength(): Int = preferences.getInt(TIMER_LENGTH, 1)
    fun getTimerLengthSeconds(): Long = preferences.getLong(TIMER_LENGTH_SECONDS, 0)
    fun setTimerLengthSeconds(seconds: Long) {
        preferences.edit().putLong(TIMER_LENGTH_SECONDS, seconds).apply()
    }

    fun getTimerState(): TimerFragment.State {
        val ordinal = preferences.getInt(
            TIMER_STATE, TimerFragment.State.Running.ordinal
        )
        return TimerFragment.State.values()[ordinal]
    }

    fun setTimerState(state: TimerFragment.State) {
        preferences.edit().putInt(TIMER_STATE, state.ordinal).apply()
    }

    companion object User {
        private const val user = "_default_user"
        const val BATTERY_NOTIFICATION = "battery_notification"
        const val NONE = "_none_value"

        private const val TIMER_LENGTH = "com.example.nekodiary.timer_length"
        private const val TIMER_LENGTH_SECONDS = "com.example.nekodiary.timer_length_seconds"
        private const val TIMER_STATE = "com.example.nekodiary.timer_state"
    }
}