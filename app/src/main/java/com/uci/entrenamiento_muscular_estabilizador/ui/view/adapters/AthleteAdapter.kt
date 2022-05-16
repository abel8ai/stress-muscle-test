package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity

class AthleteAdapter(private val athletes: List<AthleteEntity>):RecyclerView.Adapter<AthleteViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AthleteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AthleteViewHolder(layoutInflater.inflate(R.layout.item_document, parent, false))
    }

    override fun onBindViewHolder(holder: AthleteViewHolder, position: Int) {
        val item = athletes[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = athletes.size
}