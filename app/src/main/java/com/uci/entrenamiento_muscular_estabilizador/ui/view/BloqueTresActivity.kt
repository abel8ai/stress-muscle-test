package com.uci.entrenamiento_muscular_estabilizador.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

@AndroidEntryPoint
class BloqueTresActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueTresBinding

    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var athlete: AthleteEntity? = null
    private var practicant: PracticantEntity? = null
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueTresBinding.inflate(layoutInflater)
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

        binding.btnCang.setOnClickListener {
            if (isAthlete) {
                athlete!!.measureCang = binding.etValorCang.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    athleteViewModel.updateAthlete(athlete!!)
                }
            } else if (isPracticant) {
                practicant!!.measureCang = binding.etValorCang.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.updatePracticant(practicant!!)
                }
            }
        }
        binding.btnCuad.setOnClickListener {
            if (isAthlete) {
                athlete!!.measureIsocuad = binding.etValorCuad.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    athleteViewModel.updateAthlete(athlete!!)
                }
            } else if (isPracticant) {
                practicant!!.measureIsocuad = binding.etValorCuad.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.updatePracticant(practicant!!)
                }
            }
        }
        binding.btnPd.setOnClickListener {
            if (isAthlete) {
                athlete!!.measurePd = binding.etValorPd.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    athleteViewModel.updateAthlete(athlete!!)
                }
            } else if (isPracticant) {
                practicant!!.measurePd = binding.etValorPd.text.toString().toDouble()
                CoroutineScope(Dispatchers.IO).launch {
                    practicantViewModel.updatePracticant(practicant!!)
                }
            }
        }
    }
}