package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityTestsBinding

class TestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}