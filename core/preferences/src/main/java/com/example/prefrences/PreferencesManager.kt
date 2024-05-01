package com.example.prefrences

import android.content.SharedPreferences

interface PreferencesManager {

    fun getPreferences(): SharedPreferences

    fun putString(key: String, value: String)

    fun getString(key: String): String?

    fun putLong(key: String, value: Long)

    fun getLong(key: String): Long
}