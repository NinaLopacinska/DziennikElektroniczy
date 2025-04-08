package com.projekt.dzienniczek.ui.ocenyUczen


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.model.AdapterViewType
import com.projekt.dzienniczek.model.ListaKontaktow
import com.projekt.dzienniczek.model.ListaOcen


class OcenyAdapter(
    var listaOcen: List<ListaOcen>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == AdapterViewType.VIEW_TYPE_HEADER.viewType) {
            view = inflater.inflate(R.layout.header_ocena, parent, false)
            return VHHeader(view)
        } else if (viewType == AdapterViewType.VIEW_TYPE_ITEM.viewType) {
            view = inflater.inflate(R.layout.item_ocena, parent, false)
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
                    ocena.text = "Średnia: " + item.grade
                }
            }

            AdapterViewType.VIEW_TYPE_ITEM.viewType -> {
                (holder as VHItem).apply {
                    data.text = item.name
                    ocena.text = item.grade.toString()
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
        val ocena: TextView = itemView.findViewById(R.id.ocena)
    }

    internal inner class VHHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val przedmiot: TextView = itemView.findViewById(R.id.przedmiot)
        val ocena: TextView = itemView.findViewById(R.id.ocena)
    }
}