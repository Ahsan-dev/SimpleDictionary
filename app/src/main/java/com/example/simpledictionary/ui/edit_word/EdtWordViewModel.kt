package com.example.simpledictionary.ui.edit_word

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
class EdtWordViewModel @Inject constructor(private val repo: SimpleDictDBRepository): ViewModel() {

    private val _wordAdded = MutableLiveData<Boolean>()
    val wordAdded: LiveData<Boolean> get() = _wordAdded


    fun addWord(word: EnglishWords){
        viewModelScope.launch {
            repo.addWord(word)
            _wordAdded.value = true
        }
    }

}