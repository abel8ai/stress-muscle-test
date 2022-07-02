package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBibliographyBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding
import java.util.*

class BibliographyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBibliographyBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "es")
        setLocale(lang!!)
        binding = ActivityBibliographyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //actionbar - set back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.bibliograf_a)
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