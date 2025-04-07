package com.projekt.dzienniczek.model

class ListaKontaktow (
    val name: String,
    val viewType: KontaktViewType,
    val emailList: List<String?>
)

enum class KontaktViewType(val viewType: Int) {
    VIEW_TYPE_ITEM(0), VIEW_TYPE_HEADER(1)
}