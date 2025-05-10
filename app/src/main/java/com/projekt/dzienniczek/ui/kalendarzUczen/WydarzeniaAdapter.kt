package com.projekt.dzienniczek.ui.kalendarzUczen


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.model.AdapterViewType
import com.projekt.dzienniczek.model.Event
import com.projekt.dzienniczek.model.ListEvents
import com.projekt.dzienniczek.model.ListaOcen


class WydarzeniaAdapter(
    var listaOcen: List<ListEvents>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == AdapterViewType.VIEW_TYPE_HEADER.viewType) {
            view = inflater.inflate(R.layout.header_przedmiot, parent, false)
            return VHHeader(view)
        } else if (viewType == AdapterViewType.VIEW_TYPE_ITEM.viewType) {
            view = inflater.inflate(R.layout.item_wydarzenie, parent, false)
            return VHItem(view)
        }
        throw java.lang.RuntimeException(("There is no type that matches the type $viewType").toString() + ". Make sure you are using view types correctly!")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listaOcen[position]

        when (holder.itemViewType) {
            AdapterViewType.VIEW_TYPE_HEADER.viewType -> {
                (holder as VHHeader).apply {
                    przedmiot.text = item.name
                }
            }

            AdapterViewType.VIEW_TYPE_ITEM.viewType -> {
                (holder as VHItem).apply {
                    data.text = item.name
                    opis.text = item.value
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listaOcen.size
    }

    override fun getItemViewType(position: Int): Int {
        return listaOcen[position].viewType.viewType
    }

    internal inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val data: TextView = itemView.findViewById(R.id.data)
        val opis: TextView = itemView.findViewById(R.id.opis)
    }

    internal inner class VHHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val przedmiot: TextView = itemView.findViewById(R.id.przedmiot)
    }
}