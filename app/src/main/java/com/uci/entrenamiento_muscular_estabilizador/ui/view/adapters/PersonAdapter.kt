package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity

class PersonAdapter(private val persons: List<PersonEntity>):RecyclerView.Adapter<PersonViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PersonViewHolder(layoutInflater.inflate(R.layout.item_document, parent, false))
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val item = persons[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = persons.size
}