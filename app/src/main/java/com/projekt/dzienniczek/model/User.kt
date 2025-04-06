package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val email: String? = null,
    val id_klasa: Int? = null,
    val id_przedmiotu: Int? = null,
    val imie: String? = null,
    val nazwisko: String? = null,
    val nickname: String? = null,
    val rola: Int? = null
)