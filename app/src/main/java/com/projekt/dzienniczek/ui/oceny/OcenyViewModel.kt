package com.projekt.dzienniczek.ui.oceny

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OcenyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is registration Fragment"
    }
    val text: LiveData<String> = _text
}