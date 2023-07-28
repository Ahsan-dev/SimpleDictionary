package com.example.simpledictionary.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.simpledictionary.db.entities.EnglishWordsEntryContract
import com.example.simpledictionary.db.entities.EnglishWordsEntryContract.Companion.COLUMN_ID
import com.example.simpledictionary.db.entities.EnglishWordsEntryContract.Companion.COLUMN_WORD
import com.example.simpledictionary.db.entities.EnglishWordsEntryContract.Companion.TABLE_ENGLISH_WORDS
import com.example.simpledictionary.db.utils.DBPrefs
import com.example.simpledictionary.models.EnglishWords
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Error
import javax.inject.Inject

class DictionaryDBHelper @Inject constructor(private var context:Context, private var dbPrefs: DBPrefs): SQLiteOpenHelper(context, DB_NAME,null, DB_VERSION) {

    companion object{
        private val DB_NAME = "simple_dict.db"
        private val DB_VERSION = 1
        val DB_CREATED = "DB_CREATED"
        private val TAble_CREATE_QUERY = """
                create table $TABLE_ENGLISH_WORDS (
                    ${EnglishWordsEntryContract.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                    $COLUMN_WORD TEXT, 
                    ${EnglishWordsEntryContract.COLUMN_TYPE} TEXT,
                    ${EnglishWordsEntryContract.COLUMN_MEANING} TEXT
                );
                """
        private val TABLE_DROP_QUERY = "drop table if exists $TABLE_ENGLISH_WORDS;"
    }

    private var mCreatedDB = false
    private var mUpdatedDB = false

    override fun onCreate(db: SQLiteDatabase?) {
//        db?.execSQL(TAble_CREATE_QUERY)
        mCreatedDB = true
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
//        db?.execSQL(TABLE_DROP_QUERY)
//        onCreate(db)
        if(newVersion>oldVersion){
            mUpdatedDB = true
        }
    }

    override fun onOpen(db: SQLiteDatabase?) {
//        super.onOpen(db)
        if(mCreatedDB){
            mCreatedDB = false
            //Copy Database
            copyDBFromAssets(db)

        }else if (mUpdatedDB){
            mUpdatedDB = false
            //Copy Database
            copyDBFromAssets(db)
        }

    }

    private fun copyDBFromAssets(db: SQLiteDatabase?){
        var inputStream: InputStream? = null
        var fileOutputStream: FileOutputStream? = null

        try {
            inputStream = context.assets.open(DB_NAME)
            fileOutputStream = FileOutputStream(db?.path)

            var buffer = ByteArray(1024)
            var length = inputStream.read(buffer)
            while (length>0){
                fileOutputStream.write(buffer,0,length)
                length = inputStream.read(buffer)
            }
            fileOutputStream.flush()

            //Validation
            val copiedDB = context.openOrCreateDatabase(DB_NAME,0,null)
            var isDBCreated = copiedDB != null

            copiedDB.execSQL("PRAGMA user_version = $DB_VERSION")
            copiedDB.close()

            //Saving DB Status
            dbPrefs.saveDBCreatedStatus(DB_CREATED, isDBCreated)

        }catch (e: IOException){
            e.printStackTrace()
            throw Error("CopyDBFromAssets: Error in copying Database")
        }finally {
            try {
                inputStream?.close()
                fileOutputStream?.close()
            }catch (e:IOException){
                e.printStackTrace()
                throw Error("CopyDBFromAssets: Error in closing stream")
            }
        }
    }

    fun addWord(engWord: EnglishWords){
        val contentValues = ContentValues()
        val db = writableDatabase

        var lastIndex = -1L

        val cursor = db.rawQuery("SELECT MAX($COLUMN_ID) FROM $TABLE_ENGLISH_WORDS", null)

        cursor?.let {
            if(it.moveToFirst()){
                lastIndex = it.getLong(0)
            }
            it.close()
        }


        contentValues.apply {
            put(COLUMN_ID, lastIndex+1)
            put(COLUMN_WORD, engWord.word)
            put(EnglishWordsEntryContract.COLUMN_TYPE, engWord.type)
            put(EnglishWordsEntryContract.COLUMN_MEANING, engWord.meaning)
        }
        db.insert(TABLE_ENGLISH_WORDS, null, contentValues)
    }

    fun getAllWords(prefix: String = ""):Cursor{
        //dbPrefs.saveDBCreatedStatus("KEY",true)
        if(prefix.isBlank()){
            return readableDatabase.query(
                TABLE_ENGLISH_WORDS,
                null,
                null,
                null,
                null,
                null,
                "${COLUMN_WORD} ASC"
            )
        }else{
            return readableDatabase.query(
                TABLE_ENGLISH_WORDS,
                null,
                "$COLUMN_WORD like ?",
                arrayOf("$prefix%"),
                null,
                null,
                "${COLUMN_WORD} ASC"
            )
        }
    }

    fun getWordDetails(id: Int):EnglishWords{
        val cursor = readableDatabase.query(
            TABLE_ENGLISH_WORDS,
            null,
            "${EnglishWordsEntryContract.COLUMN_ID} = ? ",
            arrayOf("$id"),
            null,
            null,
            null
        )

        cursor.moveToFirst()
        val word =  EnglishWords(
            id = cursor.getInt(cursor.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_ID)),
            word = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WORD)),
            type = cursor.getString(cursor.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_TYPE)),
            meaning = cursor.getString(cursor.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_MEANING))
        )
        cursor.close()
        return word
    }
}