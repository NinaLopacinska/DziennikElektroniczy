package com.projekt.dzienniczek.ui.frekwencja

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FrekwencjaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Frekwencja Fragment"
    }
    val text: LiveData<String> = _text
}