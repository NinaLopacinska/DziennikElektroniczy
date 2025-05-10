package com.projekt.dzienniczek.ui.kalendarzUczen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.Event
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.TripleMediatorLiveData
import java.util.Date

class KalendarzUczenViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    val subject: MutableLiveData<List<Subject>> by lazy { MutableLiveData() }
    val user: MutableLiveData<List<User>> by lazy { MutableLiveData() }
    val event: MutableLiveData<List<Event>> by lazy { MutableLiveData() }
    private val errorMessage = MutableLiveData<String>()

    fun getSubject() {
        database.collection("przedmioty").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<Subject>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(Subject::class.java).apply { id = it.id.toLong() })
                    }
                    subject.value = list
                } else {
                    Log.d("NO doc", "No such document")
                    errorMessage.value = "No such document"
                }
            } else {
                Log.d("ERROR", "get failed with ", task.exception)
                errorMessage.value = task.exception.toString()
            }
        }
    }

    fun getUserData() {
        database.collection("uzytkownicy").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<User>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(User::class.java).apply { id = it.id })
                    }
                    user.value = list
                } else {
                    Log.d("NO doc", "No such document")
                    errorMessage.value = "No such document"
                }
            } else {
                Log.d("ERROR", "get failed with ", task.exception)
                errorMessage.value = task.exception.toString()
            }
        }
    }

    fun getEvent() {
        database.collection("wydarzenia").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<Event>().toMutableList()
                    document?.forEachIndexed { index, it ->
                        if(index != 0) {
                            list.add(it.toObject(Event::class.java))
                        }
                    }
                    event.value = list
                } else {
                    Log.d("NO doc", "No such document")
                    errorMessage.value = "No such document"
                }
            } else {
                Log.d("ERROR", "get failed with ", task.exception)
                errorMessage.value = task.exception.toString()
            }
        }
    }



    val userSubjectAndUserAndEventDataLiveData = TripleMediatorLiveData(subject, user, event)
    val errorMessageLiveData: LiveData<String> = errorMessage
}