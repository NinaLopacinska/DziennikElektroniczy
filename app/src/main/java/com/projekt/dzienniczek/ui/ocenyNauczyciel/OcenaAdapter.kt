package com.projekt.dzienniczek.ui.ocenyNauczyciel

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.projekt.dzienniczek.R
import com.projekt.dzienniczek.model.SchoolClass
import com.projekt.dzienniczek.model.Subject


class OcenaAdapter(
    activity: Activity,
    subjects: Array<String>
) : ArrayAdapter<String>(activity, R.layout.item_spinner, R.id.text, subjects) {

    val flater: LayoutInflater = activity.layoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView

        if(view == null){
            view = flater.inflate(R.layout.item_spinner_dropdown,parent, false);
        }

        val txtTitle = view!!.findViewById(R.id.text) as TextView
        txtTitle.text = getItem(position)

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View? = convertView

        if(view == null){
            view = flater.inflate(R.layout.item_spinner,parent, false);
        }

        val txtTitle = view!!.findViewById(R.id.text) as TextView
        txtTitle.text = getItem(position)

        return view
    }

}