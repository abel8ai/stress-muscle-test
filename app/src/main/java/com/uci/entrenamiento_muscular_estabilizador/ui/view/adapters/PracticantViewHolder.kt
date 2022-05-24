package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.PersonDetailsActivity

class PracticantViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val binding = ItemDocumentBinding.bind(view)
    fun bind(practicant:PracticantEntity){
        binding.tv1.text = practicant.fullName
        binding.cvElement.setOnClickListener{
            val intent = Intent(binding.root.context,PersonDetailsActivity::class.java)
            intent.putExtra("person",practicant)
            binding.root.context.startActivity(intent)
        }

    }
}