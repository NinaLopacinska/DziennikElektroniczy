package com.projekt.dzienniczek.ui.wiadomosci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WiadomosciViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Wiadomosci Fragment"
    }
    val text: LiveData<String> = _text
}