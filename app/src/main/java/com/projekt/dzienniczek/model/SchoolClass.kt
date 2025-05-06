package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class SchoolClass(
    var id_klasa: String? = null,
    val nazwa: String? = null
)