package com.projekt.dzienniczek.ui.kalendarzNauczyciel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject
import com.projekt.dzienniczek.model.User
import com.projekt.dzienniczek.utils.PairMediatorLiveData
import com.projekt.dzienniczek.utils.TripleMediatorLiveData
import java.util.Date

class KalendarzNauczycielViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    val subject: MutableLiveData<List<Subject>> by lazy { MutableLiveData() }
    val schooldata: MutableLiveData<List<SchoolClass>> by lazy { MutableLiveData() }
    private val errorMessage = MutableLiveData<String>()
    private val message = MutableLiveData<String>()

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

    fun getClass() {
        database.collection("klasy").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document.isEmpty) {
                    val list = emptyList<SchoolClass>().toMutableList()
                    document?.forEachIndexed { index, it ->
                        if(index != 0) {
                            list.add(it.toObject(SchoolClass::class.java).apply { id_klasa = it.id })
                        }
                    }
                    schooldata.value = list
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

    fun sentEvent(
        przedmiot: Long,
        klasa: Long,
        date: Date,
        opis: String,
        nauczyciel: String
    ) {

        val docData = hashMapOf(
            "data" to Timestamp(date),
            "id_przedmiotu" to przedmiot,
            "id_klasa" to klasa,
            "opis" to opis,
            "id_nauczyciel" to nauczyciel,
        )

        database.collection("wydarzenia")
            .add(docData)
            .addOnSuccessListener { message.value = "Wydarzenie została zapisana" }
            .addOnFailureListener { message.value =  "Wydarzenie nie została zapisana" }


    }

    val userSubjectAndSchooldataLiveData = PairMediatorLiveData(subject, schooldata)
    val errorMessageLiveData: LiveData<String> = errorMessage
    val messageLiveData: LiveData<String> = message
}