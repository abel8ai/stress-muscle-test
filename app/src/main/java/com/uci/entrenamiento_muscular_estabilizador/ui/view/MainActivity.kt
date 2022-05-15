package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btPracticantList.setOnClickListener {
            val intent = Intent(this,PersonsActivity::class.java)
            intent.putExtra("person_type","practicant")
            startActivity(intent)
        }
        binding.btAthleteList.setOnClickListener {
            val intent = Intent(this,PersonsActivity::class.java)
            intent.putExtra("person_type","athlete")
            startActivity(intent)
        }




    }


}