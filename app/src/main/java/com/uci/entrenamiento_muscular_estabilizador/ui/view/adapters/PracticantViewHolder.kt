package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding

class PracticantViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val binding = ItemDocumentBinding.bind(view)
    fun bind(practicant:PracticantEntity){
        binding.tvDocName.text = practicant.fullName
        binding.cvElement.setOnClickListener{

        }
    }
}