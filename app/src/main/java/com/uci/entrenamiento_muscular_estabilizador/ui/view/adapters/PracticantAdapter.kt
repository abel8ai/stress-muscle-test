package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity

class PracticantAdapter(private val practicants: List<PracticantEntity>):RecyclerView.Adapter<PracticantViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PracticantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PracticantViewHolder(layoutInflater.inflate(R.layout.item_document, parent, false))
    }

    override fun onBindViewHolder(holder: PracticantViewHolder, position: Int) {
        val item = practicants[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = practicants.size
}