package com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ItemDocumentBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.PersonDetailsActivity

class PracticantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemDocumentBinding.bind(view)
    fun bind(practicant: PracticantEntity) {
        binding.tvNombre.text = practicant.fullName
        binding.tvEdad.text = practicant.age.toString()
        binding.tvEstatura.text = practicant.height.toString()
        binding.tvPeso.text = practicant.weight.toString()
        binding.tvSexo.text = practicant.gender
        binding.cvElement.setOnClickListener {
            val intent = Intent(binding.root.context, PersonDetailsActivity::class.java)
            intent.putExtra("practicant_id", practicant.id)
            intent.putExtra("person_type", "practicant")
            binding.root.context.startActivity(intent)
        }

    }
}