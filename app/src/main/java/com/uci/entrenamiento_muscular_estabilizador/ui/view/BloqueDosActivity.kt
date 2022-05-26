package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueDosBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.btnCs.setOnClickListener {
            if (isAthlete) {
                athlete!!.measureCs = binding.etValorCs.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    athleteViewModel.updateAthlete(athlete!!)
                }
            } else if (isPracticant) {
                practicant!!.measureCs = binding.etValorCs.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.updatePracticant(practicant!!)
                }
            }
        }
        binding.btnCn.setOnClickListener {
            if (isAthlete) {
                athlete!!.measureCn = binding.etValorCn.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    athleteViewModel.updateAthlete(athlete!!)
                }
            } else if (isPracticant) {
                practicant!!.measureCn = binding.etValorCn.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.updatePracticant(practicant!!)
                }
            }
        }
    }
}