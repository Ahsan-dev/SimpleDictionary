package com.example.simpledictionary.db.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class DBPrefs @Inject constructor(private val sharedPreferences: SharedPreferences) {
    fun saveDBCreatedStatus(key: String, value: Boolean){
        sharedPreferences
            .edit()
            .putBoolean(key,value)
            .apply()
    }

    fun getDBCreatedStatus(key: String) : Boolean{
        return sharedPreferences.getBoolean(key,false)
    }
}