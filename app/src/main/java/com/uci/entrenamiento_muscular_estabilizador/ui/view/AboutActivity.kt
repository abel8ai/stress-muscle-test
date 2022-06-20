package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityAboutBinding
import java.util.*


class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    private lateinit var sharedPref: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "es")
        setLocale(lang!!)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = resources.getString(R.string.sobre_la_apk)
        //set back button
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.cvPdf.setOnClickListener {
            val intent = Intent(this, PdfReaderActivity::class.java)
            if (lang == "es")
                intent.putExtra("doc_name", "ENTRENAMIENTO MÚSCULOS ESTABILIZADORES.pdf")
            else if (lang == "en")
                intent.putExtra("doc_name", "STABILIZER MUSCLE TRAINING.pdf")
            startActivity(intent)
        }
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
        resources.updateConfiguration(config, resources.displayMetrics)
        onConfigurationChanged(config)
    }


}