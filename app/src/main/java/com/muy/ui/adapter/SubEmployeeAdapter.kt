package com.muy.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muy.App
import com.muy.R
import com.muy.data.dto.EmployeeItem
import kotlinx.android.synthetic.main.row_subemployees.view.*

class SubEmployeeAdapter(val items : List<EmployeeItem>) : RecyclerView.Adapter<ViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(App.applicationContext()).inflate(R.layout.row_subemployees, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAnimalType.text = items[position].name
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val tvAnimalType = view.textName
}