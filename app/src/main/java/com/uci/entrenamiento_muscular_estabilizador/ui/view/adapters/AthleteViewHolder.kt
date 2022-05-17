package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.PersonDetailsActivity

class AthleteViewHolder(view:View):RecyclerView.ViewHolder(view){

    private val binding = ItemDocumentBinding.bind(view)
    fun bind(athlete:AthleteEntity){
        binding.tvDocName.text = athlete.fullName
        binding.cvElement.setOnClickListener{
            val intent = Intent(binding.root.context,PersonDetailsActivity::class.java)
            intent.putExtra("person",athlete)
            binding.root.context.startActivity(intent)
        }
    }
}