package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Event(
    val data: Date? = null,
    val id_klasa: Int? = null,
    val id_nauczyciel: String? = null,
    val id_przedmiotu: Long? = null,
    val opis: String? = null
)