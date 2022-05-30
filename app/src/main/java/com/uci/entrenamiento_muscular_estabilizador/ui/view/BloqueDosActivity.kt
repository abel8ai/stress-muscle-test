package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueDosBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueUnoBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BloqueDosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueDosBinding

    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var athlete: AthleteEntity? = null
    private var practicant: PracticantEntity? = null
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueDosBinding.inflate(layoutInflater)
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
        binding.btnCs.setOnClickListener {
            if (binding.etValorCs.text.isNotEmpty()) {
                if (isAthlete) {
                    Toast.makeText(this, R.string.saved_result, Toast.LENGTH_SHORT)
                    athlete!!.measureCs = binding.etValorCs.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                    }
                } else if (isPracticant) {
                    Toast.makeText(this, R.string.saved_result, Toast.LENGTH_SHORT)
                    practicant!!.measureCs = binding.etValorCs.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                    }
                }
            } else {
                binding.etValorCs.hint = hint
                binding.etValorCs.setHintTextColor(Color.RED)
            }
        }
        binding.btnCn.setOnClickListener {
            if (binding.etValorCn.text.isNotEmpty()) {
                if (isAthlete) {
                    Toast.makeText(this, R.string.saved_result, Toast.LENGTH_SHORT)
                    athlete!!.measureCn = binding.etValorCn.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                    }
                } else if (isPracticant) {
                    Toast.makeText(this, R.string.saved_result, Toast.LENGTH_SHORT)
                    practicant!!.measureCn = binding.etValorCn.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                    }
                }
            }else{
                binding.etValorCn.hint = hint
                binding.etValorCn.setHintTextColor(Color.RED)
            }
        }
    }
}