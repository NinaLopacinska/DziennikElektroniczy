package com.projekt.dzienniczek.ui.listaObecnosci

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaObecnosciViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ListaObecnosci Fragment"
    }
    val text: LiveData<String> = _text
}