package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueUnoBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding

class BloqueUnoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueUnoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueUnoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}