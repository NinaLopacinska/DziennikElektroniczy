package com.projekt.dzienniczek.model

enum class Role(val value: Int) {
    NAUCZYCIEL(1), UCZEN(2);

    companion object {
        fun findRole(value: Int): Role? = entries.find { it.value == value }
    }
}