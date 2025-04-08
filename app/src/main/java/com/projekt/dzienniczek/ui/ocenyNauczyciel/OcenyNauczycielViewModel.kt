package com.projekt.dzienniczek.ui.ocenyNauczyciel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.Grade
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.PairMediatorLiveData
import com.projekt.dzienniczek.utils.TripleMediatorLiveData
import org.w3c.dom.Text
import java.util.Date

class OcenyNauczycielViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    private val _subject = MutableLiveData<List<Subject>>()
    val subject: LiveData<List<Subject>> = _subject
    private val _user = MutableLiveData<List<User>>()
    val user: LiveData<List<User>> = _user
    private val _schooldata = MutableLiveData<List<SchoolClass>>()
    val schooldata: LiveData<List<SchoolClass>> = _schooldata
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

    fun getUserData() {
        database.collection("uzytkownicy").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<User>().toMutableList()
                    document?.forEach {
                        list.add(it.toObject(User::class.java))
                    }
                    _user.value = list
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

    fun sentGrade(
        przedmiot: String,
        uczen: String,
        date: Date,
        ocena: Double,
        nauczyciel: String
    ) {

        val docData = hashMapOf(
            "data" to Timestamp(date),
            "id_przedmiotu" to przedmiot,
            "id_uzyt" to uczen,
            "ocena" to ocena,
            "id_nauczyciel" to nauczyciel,
        )

        database.collection("oceny")
            .add(docData)
            .addOnSuccessListener { message.value = "DocumentSnapshot successfully written!" }
            .addOnFailureListener { message.value =  "Error writing document" }


    }

    val userSubjectAndUserAndSchooldataLiveData = TripleMediatorLiveData(subject, user, schooldata)
    val errorMessage: LiveData<String> = message
}