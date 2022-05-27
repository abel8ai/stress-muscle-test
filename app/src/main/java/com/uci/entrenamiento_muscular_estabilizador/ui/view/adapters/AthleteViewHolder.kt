package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.PersonDetailsActivity

class AthleteViewHolder(view:View):RecyclerView.ViewHolder(view){

    val binding = ItemDocumentBinding.bind(view)
    fun bind(athlete:AthleteEntity){
        binding.tvNombre.text = athlete.fullName
        binding.tvEdad.text = athlete.age.toString()
        binding.tvEstatura.text = athlete.height.toString()
        binding.tvPeso.text = athlete.weight.toString()
        binding.tvSexo.text = athlete.gender
        binding.cvElement.setOnClickListener{
            val intent = Intent(binding.root.context,PersonDetailsActivity::class.java)
            intent.putExtra("athlete_id",athlete.id)
            intent.putExtra("person_type", "athlete")
            binding.root.context.startActivity(intent)
        }
    }
}