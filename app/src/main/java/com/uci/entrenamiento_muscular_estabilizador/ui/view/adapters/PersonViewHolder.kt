package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding

class PersonViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val binding = ItemDocumentBinding.bind(view)
    fun bind(person:PersonEntity){
        binding.tvDocName.text = person.fullName
        binding.cvElement.setOnClickListener{

        }
    }
}