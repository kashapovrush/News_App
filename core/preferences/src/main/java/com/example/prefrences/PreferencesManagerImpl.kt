package com.example.prefrences

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferencesManagerImpl @Inject constructor(private val context: Context): PreferencesManager {

    override fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(EXTRA_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun putString(key: String, value: String) {
        val editor = getPreferences().edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getString(key: String): String? {
        return getPreferences().getString(key, null)
    }

    override fun putLong(key: String, value: Long) {
        val editor = getPreferences().edit()
        editor.putLong(key, value)
        editor.apply()
    }

    override fun getLong(key: String): Long {
        return getPreferences().getLong(key, 0)
    }

    companion object {

        const val EXTRA_PREFERENCES = "preferences"
    }

}