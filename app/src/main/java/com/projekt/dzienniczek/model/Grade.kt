package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Grade(
    val data: Date? = null,
    val id_przedmiotu: Int? = null,
    val id_uzyt: Int? = null,
    val image: String? = null,
    val ocena: Int? = null
)