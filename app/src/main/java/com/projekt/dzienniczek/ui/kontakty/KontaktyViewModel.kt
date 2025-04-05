package com.projekt.dzienniczek.ui.kontakty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KontaktyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Kontakty Fragment"
    }
    val text: LiveData<String> = _text
}