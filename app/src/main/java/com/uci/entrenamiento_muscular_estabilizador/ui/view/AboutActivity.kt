package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //actionbar - set back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getString(R.string.sobre_la_apk)
        binding.cvPdf.setOnClickListener {
            val intent = Intent(this, PdfReaderActivity::class.java)
            intent.putExtra("doc_name", "ENTRENAMIENTO MÚSCULOS ESTABILIZADORES.pdf")
            startActivity(intent)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}