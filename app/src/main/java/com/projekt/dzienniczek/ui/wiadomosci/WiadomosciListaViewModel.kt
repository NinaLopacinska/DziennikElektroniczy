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
import com.projekt.dzienniczek.model.Grade
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.PairMediatorLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class WiadomosciListaViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    val user: MutableLiveData<List<User>> by lazy { MutableLiveData() }
    val schooldata: MutableLiveData<List<SchoolClass>> by lazy { MutableLiveData() }
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
                    user.value = list
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
                    schooldata.value = list
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

    val userDataAndSchoolClassLiveData = PairMediatorLiveData(user, schooldata)
    val errorMessage: LiveData<String> = message
}