package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPdfReaderBinding

class PdfReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfReaderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pdfName = intent.getStringExtra("doc_name")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.documento)
        binding.pdfView
            .fromAsset("$pdfName")
            .defaultPage(0)
            .enableSwipe(true)
            .load()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}