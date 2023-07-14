package com.example.simpledictionary.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences("dict_shared_prefs", Context.MODE_PRIVATE)
    }

}