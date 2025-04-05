package com.projekt.dzienniczek.ui.oceny_nauczyciel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OcenyNauczycielViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Oceny Fragment"
    }
    val text: LiveData<String> = _text
}