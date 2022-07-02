package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueTresBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueUnoBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class BloqueTresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueTresBinding
    private lateinit var sharedPref: SharedPreferences
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var athlete: AthleteEntity? = null
    private var practicant: PracticantEntity? = null
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "es")
        setLocale(lang!!)
        binding = ActivityBloqueTresBinding.inflate(layoutInflater)
        supportActionBar!!.title = resources.getString(R.string.dialog_block_3)
        setContentView(binding.root)
        context = this
        practicantViewModel
        athleteViewModel
        isAthlete = intent.extras!!.getString("person_type").equals("athlete")
        isPracticant = intent.extras!!.getString("person_type").equals("practicant")

        if (isAthlete) {
            CoroutineScope(Dispatchers.IO).launch {
                athleteViewModel.getAthleteById(intent.extras!!.getInt("athlete_id"))
            }
        } else if (isPracticant) {
            CoroutineScope(Dispatchers.IO).launch {
                practicantViewModel.getPracticantById(intent.extras!!.getInt("practicant_id"))
            }
        }

        practicantViewModel.practicantModel.observe(this, Observer {
            practicant = it
        })
        athleteViewModel.athleteModel.observe(this, Observer {
            athlete = it
        })
        val hint = resources.getString(R.string.mandatory_field)
        binding.btnCang.setOnClickListener {
            if (binding.etValorCang.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measureCang = binding.etValorCang.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measureCang = binding.etValorCang.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorCang.hint = hint
                binding.etValorCang.setHintTextColor(Color.RED)
            }
        }
        binding.btnCuad.setOnClickListener {
            if (binding.etValorCuad.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measureIsocuad = binding.etValorCuad.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measureIsocuad = binding.etValorCuad.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorCuad.hint = hint
                binding.etValorCuad.setHintTextColor(Color.RED)
            }
        }
        binding.btnPd.setOnClickListener {
            if (binding.etValorPd.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measurePd = binding.etValorPd.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measurePd = binding.etValorPd.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }else{
                binding.etValorPd.hint = hint
                binding.etValorPd.setHintTextColor(Color.RED)
            }
        }
        //actionbar - set back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
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