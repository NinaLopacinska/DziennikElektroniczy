package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Subject(
    var id: Long? = null,
    val id_uzyt: String? = null,
    val nazwa: String? = null
)