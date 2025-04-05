package com.projekt.dzienniczek.ui.ustawienia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UstawieniaViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Ustawienia Fragment"
    }
    val text: LiveData<String> = _text
}