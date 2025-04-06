package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Class(
    val id_klasa: Int? = null,
    val nazwa: String? = null
)