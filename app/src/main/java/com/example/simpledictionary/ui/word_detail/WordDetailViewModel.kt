package com.example.simpledictionary.ui.word_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpledictionary.models.EnglishWords
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordDetailViewModel @Inject constructor(
    private val repo: SimpleDictDBRepository
    ): ViewModel() {
    private val _word = MutableLiveData<EnglishWords>()
    val word: LiveData<EnglishWords> get() = _word

    fun getWordDetails(id:Int){
        viewModelScope.launch {
            val res = repo.getWordDetail(id)
            _word.value = res
        }
    }


}