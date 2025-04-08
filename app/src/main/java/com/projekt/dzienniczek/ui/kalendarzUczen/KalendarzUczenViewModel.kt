package com.projekt.dzienniczek.ui.kalendarzUczen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KalendarzUczenViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Kalendarz Fragment"
    }
    val text: LiveData<String> = _text
}