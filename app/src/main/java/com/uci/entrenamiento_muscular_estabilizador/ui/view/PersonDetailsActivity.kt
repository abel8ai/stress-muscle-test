package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPersonDetailsBinding

class PersonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val person = intent.extras!!.get("person")
        if (person is AthleteEntity){
            binding.tv1.text = person.fullName
            binding.tv2.text = person.gender
            binding.tv3.text = person.municipality
            binding.tv4.text = person.province
        }
        else if (person is PracticantEntity){
            binding.tv1.text = person.fullName
            binding.tv2.text = person.gender
            binding.tv3.text = person.municipality
            binding.tv4.text = person.province
        }
        binding.btBloque1.setOnClickListener {
            val intent = Intent(this,TestsActivity::class.java)
            if (person is AthleteEntity)
                intent.putExtra("person",person)
            else if (person is PracticantEntity)
                intent.putExtra("person",person)
            intent.putExtra("block",1)
            startActivity(intent)
        }
        binding.btBloque2.setOnClickListener {
            val intent = Intent(this,TestsActivity::class.java)
            if (person is AthleteEntity)
                intent.putExtra("person",person)
            else if (person is PracticantEntity)
                intent.putExtra("person",person)
            intent.putExtra("block",2)
            startActivity(intent)
        }
        binding.btBloque3.setOnClickListener {
            val intent = Intent(this,TestsActivity::class.java)
            if (person is AthleteEntity)
                intent.putExtra("person",person)
            else if (person is PracticantEntity)
                intent.putExtra("person",person)
            intent.putExtra("block",3)
            startActivity(intent)
        }
    }
}