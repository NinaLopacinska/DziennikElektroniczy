package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Grade(
    val data: Date? = null,
    val id_przedmiotu: String? = null,
    val id_uzyt: String? = null,
    val image: String? = null,
    val ocena: Double = 0.0
)