package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBibliographyBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding

class BibliographyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBibliographyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBibliographyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}