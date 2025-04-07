package com.projekt.dzienniczek.ui.wiadomosci

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.PairMediatorLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class WiadomosciListaViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    private val _data = MutableLiveData<List<User>>()
    val data: LiveData<List<User>> = _data
    private val _schooldata = MutableLiveData<List<SchoolClass>>()
    val schooldata: LiveData<List<SchoolClass>> = _schooldata
    private val message = MutableLiveData<String>()

    fun getUserData() {
        database.collection("uzytkownicy").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<User>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(User::class.java))
                    }
                    Log.d("doc", "getUserData " + list.toString())
                    _data.value = list
                } else {
                    Log.d("NO doc", "No such document")
                    message.value = "No such document"
                }
            } else {
                Log.d("ERROR", "get failed with ", task.exception)
                message.value = task.exception.toString()
            }
        }
    }

    fun getClass() {
        database.collection("klasy").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<SchoolClass>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(SchoolClass::class.java))
                    }
                    Log.d("doc", "getClass "  + list.toString())
                    _schooldata.value = list
                } else {
                    Log.d("NO doc", "No such document")
                    message.value = "No such document"
                }
            } else {
                Log.d("ERROR", "get failed with ", task.exception)
                message.value = task.exception.toString()
            }
        }
    }

    val userDataAndSchoolClassLiveData = PairMediatorLiveData(data, schooldata)
    val errorMessage: LiveData<String> = message
}