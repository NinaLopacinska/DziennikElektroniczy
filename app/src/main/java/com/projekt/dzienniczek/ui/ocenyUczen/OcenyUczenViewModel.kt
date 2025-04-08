package com.projekt.dzienniczek.ui.ocenyUczen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.Grade
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.PairMediatorLiveData

class OcenyUczenViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    private val _subject = MutableLiveData<List<Subject>>()
    val subject: LiveData<List<Subject>> = _subject
    private val _grade = MutableLiveData<List<Grade>>()
    val grade: LiveData<List<Grade>> = _grade
    private val message = MutableLiveData<String>()

    fun getSubject() {
        database.collection("przedmioty").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<Subject>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(Subject::class.java))
                    }
                    _subject.value = list
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

    fun getGrade() {
        database.collection("oceny").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<Grade>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(Grade::class.java))
                    }
                    Log.d("doc", "getClass "  + list.toString())
                    _grade.value = list
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

    val userSubjectAndGradeLiveData = PairMediatorLiveData(subject, grade)
    val errorMessage: LiveData<String> = message
}