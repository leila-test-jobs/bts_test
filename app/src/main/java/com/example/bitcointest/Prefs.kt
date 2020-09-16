package com.example.bitcointest

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class Prefs(context: Context) {
    companion object{
        private const val PREFS_FILENAME = "shared_prefs_name"

        private const val KEY_DATE = "date"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var date: Long
        get() = sharedPrefs.getLong(KEY_DATE, 0L)
        set(value) = sharedPrefs.edit { putLong(KEY_DATE,value) }
}