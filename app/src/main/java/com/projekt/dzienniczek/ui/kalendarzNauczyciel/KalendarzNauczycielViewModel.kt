package com.projekt.dzienniczek.ui.kalendarzNauczyciel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KalendarzNauczycielViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Kalendarz Fragment"
    }
    val text: LiveData<String> = _text
}