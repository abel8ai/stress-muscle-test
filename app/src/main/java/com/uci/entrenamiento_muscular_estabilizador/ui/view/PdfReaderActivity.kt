package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPdfReaderBinding
import java.util.*

class PdfReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfReaderBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "es")
        setLocale(lang!!)
        binding = ActivityPdfReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pdfName = intent.getStringExtra("doc_name")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.pdf1)
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
    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = Locale(language)
        resources.updateConfiguration(config,resources.displayMetrics)
        onConfigurationChanged(config)
    }
}