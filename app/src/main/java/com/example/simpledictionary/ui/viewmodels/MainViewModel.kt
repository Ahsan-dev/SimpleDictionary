package com.example.simpledictionary.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simpledictionary.repository.SimpleDictDBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: SimpleDictDBRepository): ViewModel() {

    private val _test = MutableLiveData<String>()
    val test: LiveData<String> get() = _test

    fun getTestValue(){
        _test.value = "Test Data"
    }

}