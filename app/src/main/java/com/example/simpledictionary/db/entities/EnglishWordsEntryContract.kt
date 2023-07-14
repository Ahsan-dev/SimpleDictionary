package com.example.simpledictionary.db.entities

import android.provider.BaseColumns

open class EnglishWordsEntryContract private constructor() : BaseColumns {
    companion object{
        val TABLE_ENGLISH_WORDS = "english_words"
        val COLUMN_ID = BaseColumns._ID
        val COLUMN_WORD = "word"
        val COLUMN_TYPE = "type"
        val COLUMN_MEANING = "meaning"
    }
}