package com.projekt.dzienniczek.model

import com.google.firebase.database.IgnoreExtraProperties
import java.util.Date

@IgnoreExtraProperties
data class Attendance(
    val data: Date? = null,
    val id_przedmiotu: Int? = null,
    val id_uzyt: Int? = null,
    val obecnosc: Boolean = false
)