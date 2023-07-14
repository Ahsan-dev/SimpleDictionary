package com.example.simpledictionary.repository

import android.database.Cursor
import com.example.simpledictionary.db.DictionaryDBHelper
import com.example.simpledictionary.models.EnglishWords
import javax.inject.Inject

class SimpleDictDBRepository @Inject constructor(private val dictionaryDBHelper: DictionaryDBHelper) {

    fun addWord(word: EnglishWords){
        dictionaryDBHelper.addWord(word)
    }

    fun getAllWords(prefix: String): Cursor{
        return dictionaryDBHelper.getAllWords(prefix)
    }

    fun getWordDetail(id: Int): EnglishWords{
        return dictionaryDBHelper.getWordDetails(id)
    }

}