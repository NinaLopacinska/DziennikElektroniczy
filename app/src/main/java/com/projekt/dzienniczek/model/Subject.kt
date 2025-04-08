package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subject(
    val id: String? = null,
    val id_uzyt: String? = null,
    val nazwa: String? = null
)