package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subject(
    val id_przedmiotu: Int? = null,
    val id_uzyt: Int? = null,
    val nazwa: String? = null
)