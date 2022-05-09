package com.uci.entrenamiento_muscular_estabilizador.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding
import com.uci.entrenamiento_muscular_estabilizador.view_model.PersonViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val personViewModel = PersonViewModel(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        personViewModel.personModel

    }
}