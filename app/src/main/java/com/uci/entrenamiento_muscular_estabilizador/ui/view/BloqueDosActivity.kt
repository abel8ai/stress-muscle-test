package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueDosBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueUnoBinding

class BloqueDosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueDosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueDosBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}