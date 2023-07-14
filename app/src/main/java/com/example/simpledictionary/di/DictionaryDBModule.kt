package com.example.simpledictionary.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.simpledictionary.db.DictionaryDBHelper
import com.example.simpledictionary.db.utils.DBPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DictionaryDBModule {

    @Provides
    fun provideDictionaryDBHelper(application: Application, dbPrefs: DBPrefs):DictionaryDBHelper{
        return DictionaryDBHelper(application,dbPrefs)
    }

    @Provides
    fun provideDBPrefs(sharedPreferences: SharedPreferences):DBPrefs{
        return DBPrefs(sharedPreferences)
    }
}