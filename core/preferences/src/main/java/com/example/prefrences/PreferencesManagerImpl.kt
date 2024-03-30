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

    companion object {

        const val EXTRA_PREFERENCES = "preferences"
    }

}