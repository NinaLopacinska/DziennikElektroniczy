package com.projekt.dzienniczek.ui.wiadomosci


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.model.KontaktViewType
import com.projekt.dzienniczek.model.ListaKontaktow


class WiadomosciAdapter(
    var listaKontaktow: List<ListaKontaktow>,
    val listener: (ListaKontaktow?) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == KontaktViewType.VIEW_TYPE_HEADER.viewType) {
            view = inflater.inflate(R.layout.header_wiadomosci, parent, false)
            return VHHeader(view)
        } else if (viewType == KontaktViewType.VIEW_TYPE_ITEM.viewType) {
            view = inflater.inflate(R.layout.item_wiadomosci, parent, false)
            return VHItem(view)
        }
        throw java.lang.RuntimeException(("There is no type that matches the type $viewType").toString() + ". Make sure you are using view types correctly!")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listaKontaktow[position]

        when (holder.itemViewType) {
            KontaktViewType.VIEW_TYPE_HEADER.viewType -> {
                (holder as VHHeader).itemHeader.apply {
                    text = item.name
                    setOnClickListener {
                        listener.invoke(item)
                    }
                }
            }

            KontaktViewType.VIEW_TYPE_ITEM.viewType -> {
                (holder as VHItem).item.apply {
                    text = item.name
                    setOnClickListener {
                        listener.invoke(item)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listaKontaktow.size
    }

    override fun getItemViewType(position: Int): Int {
        return listaKontaktow[position].viewType.viewType
    }

    internal inner class VHItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: TextView = itemView.findViewById(R.id.itemW)
    }

    internal inner class VHHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemHeader: TextView = itemView.findViewById(R.id.headerW)
    }
}