package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class SchoolClass(
    val id_klasa: Int? = null,
    val nazwa: String? = null
)