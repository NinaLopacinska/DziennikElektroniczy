package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Student(
    val imie: String? = null,
    val klasa: String? = null,
    val nazwisko: String? = null
)