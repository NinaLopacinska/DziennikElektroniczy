package com.projekt.dzienniczek.utils

import android.content.Context
import android.content.SharedPreferences
import java.lang.Boolean.getBoolean

val NOTIFICATION = "NOTIFICATION"

object AppPreferences {
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("prefs", MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isNotificationOn: Boolean
        get() = preferences.getBoolean(NOTIFICATION, true)
        set(value) = preferences.edit { it.putBoolean(NOTIFICATION, value) }
}