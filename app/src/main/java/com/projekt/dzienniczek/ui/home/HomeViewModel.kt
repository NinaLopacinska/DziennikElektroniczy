package com.projekt.dzienniczek.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.projekt.dzienniczek.model.User

class HomeViewModel : ViewModel() {

    private var database: FirebaseFirestore = Firebase.firestore
    private val data = MutableLiveData<User>()
    private val message = MutableLiveData<String>()

    fun getUserData(userId: String) {
        database.collection("uzytkownicy").document(userId).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document.exists()) {
                    data.value = document.toObject(User::class.java)
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

    val userData: LiveData<User> = data
    val errorMessage: LiveData<String> = message
}