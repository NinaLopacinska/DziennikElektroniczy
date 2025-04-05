package com.projekt.dzienniczek.ui.planZajec

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlanZajecViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is PlanZajec Fragment"
    }
    val text: LiveData<String> = _text
}