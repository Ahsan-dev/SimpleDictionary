package com.example.simpledictionary.ui.main_list

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SimpleDictDBRepository): ViewModel() {

    private val _test = MutableLiveData<String>()
    private val _allWordsCursor = MutableLiveData<Cursor>()
    val test: LiveData<String> get() = _test
    val allWordsCursor: LiveData<Cursor> get() = _allWordsCursor

    fun getTestValue(){
        _test.value = "Test Data"
    }

    fun fetchAllWords(prefix: String){
        viewModelScope.launch{
            val cursor = repository.getAllWords(prefix)
            _allWordsCursor.value = cursor
        }
    }

}