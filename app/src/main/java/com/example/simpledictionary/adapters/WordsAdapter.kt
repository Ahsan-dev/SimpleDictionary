package com.example.simpledictionary.adapters

import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import com.example.simpledictionary.R
import com.example.simpledictionary.db.entities.EnglishWordsEntryContract

class WordsAdapter(context: Context, cursor: Cursor): CursorAdapter(context,cursor,0){
    private class EnglishWordsViewHolder{
        var tvWord:TextView? = null
        var tvType:TextView? = null
        var tvMeaning:TextView? = null

        var wordColumnIndex = 0
        var typeColumnIndex = 0
        var meaningColumnIndex = 0
    }

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.words_list_item,parent,false)

        val wordsViewHolder = EnglishWordsViewHolder()
        wordsViewHolder.tvWord = view.findViewById(R.id.tv_word)
        wordsViewHolder.tvType = view.findViewById(R.id.tv_type)
        wordsViewHolder.tvMeaning = view.findViewById(R.id.tv_meaning)

        wordsViewHolder.wordColumnIndex = cursor!!.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_WORD)
        wordsViewHolder.typeColumnIndex = cursor!!.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_TYPE)
        wordsViewHolder.meaningColumnIndex = cursor!!.getColumnIndexOrThrow(EnglishWordsEntryContract.COLUMN_MEANING)

        view.tag = wordsViewHolder

        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val wordsViewHolder = view?.tag as EnglishWordsViewHolder

        wordsViewHolder.tvWord?.text = cursor?.getString(wordsViewHolder.wordColumnIndex)
        wordsViewHolder.tvType?.text = cursor?.getString(wordsViewHolder.typeColumnIndex)
        wordsViewHolder.tvMeaning?.text = cursor?.getString(wordsViewHolder.meaningColumnIndex)
    }

//    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//        val view = super.getView(position, convertView, parent)
//        if(position%2==0){
//            view.setBackgroundResource(R.color.item_secondary)
//            view.setBackgroundColor(Color.WHITE)
//        }else{
//            view.setBackgroundColor(Color.WHITE)
//        }
//        return view
//    }

}