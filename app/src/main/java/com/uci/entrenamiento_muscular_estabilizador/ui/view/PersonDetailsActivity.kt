package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPersonDetailsBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonDetailsBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var athlete: AthleteEntity? = null
    private var practicant: PracticantEntity? = null
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonDetailsBinding.inflate(layoutInflater)
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
            CoroutineScope(Dispatchers.IO).launch {
                practicantViewModel.evaluatePracticant(practicant!!)
                runOnUiThread {
                    loadResultsAndEvaluations()
                }

            }
            loadData()
        })
        athleteViewModel.athleteModel.observe(this, Observer {
            athlete = it
            CoroutineScope(Dispatchers.IO).launch {
                athleteViewModel.evaluateAthlete(athlete!!)
                runOnUiThread {
                    loadResultsAndEvaluations()
                }
            }
            loadData()

        })

        binding.btBloque1.setOnClickListener {
            val intent = Intent(this, BloqueUnoActivity::class.java)
            if (isAthlete) {
                intent.putExtra("person_type", "athlete")
                intent.putExtra("athlete_id", athlete!!.id)
            } else if (isPracticant) {
                intent.putExtra("person_type", "practicant")
                intent.putExtra("practicant_id", practicant!!.id)
            }
            startActivity(intent)
        }
        binding.btBloque2.setOnClickListener {
            val intent = Intent(this, BloqueDosActivity::class.java)
            if (isAthlete) {
                intent.putExtra("person_type", "athlete")
                intent.putExtra("athlete_id", athlete!!.id)
            } else if (isPracticant) {
                intent.putExtra("person_type", "practicant")
                intent.putExtra("practicant_id", practicant!!.id)
            }
            startActivity(intent)
        }
        binding.btBloque3.setOnClickListener {
            val intent = Intent(this, BloqueTresActivity::class.java)
            if (isAthlete) {
                intent.putExtra("person_type", "athlete")
                intent.putExtra("athlete_id", athlete!!.id)
            } else if (isPracticant) {
                intent.putExtra("person_type", "practicant")
                intent.putExtra("practicant_id", practicant!!.id)
            }
            startActivity(intent)
        }
    }

    private fun loadData() {
        if (athlete != null) {
            binding.tvName.text = athlete!!.fullName
            binding.tvGender.text = athlete!!.gender
            binding.tvEdad.text = athlete!!.age.toString()
            binding.tvPeso.text = athlete!!.weight.toString()
            binding.tvEstatura.text = athlete!!.height.toString()
            binding.tvMunicip.text = athlete!!.municipality
            binding.tvProvince.text = athlete!!.province
            binding.tvDeporte.text = athlete!!.sport
            binding.tvAnnos.text = athlete!!.yearsInSport.toString()

            // remove practicants visibility
            binding.tvCrossfit.visibility = View.GONE
            binding.tvEjercicioAerobio.visibility = View.GONE
            binding.tvYoga.visibility = View.GONE
            binding.tvSpinning.visibility = View.GONE
            binding.tvEntrenamientoMusculacion.visibility = View.GONE
            binding.tvPilates.visibility = View.GONE
            binding.tvOtro.visibility = View.GONE
        } else if (practicant != null) {
            binding.tvName.text = practicant!!.fullName
            binding.tvGender.text = practicant!!.gender
            binding.tvEstatura.text = practicant!!.height.toString()
            binding.tvEdad.text = practicant!!.age.toString()
            binding.tvPeso.text = practicant!!.weight.toString()
            binding.tvMunicip.text = practicant!!.municipality
            binding.tvProvince.text = practicant!!.province

            // remove athletes visibility
            binding.tvAnnosLabel.visibility = View.GONE
            binding.tvDeporteLabel.visibility = View.GONE

            if (!practicant!!.aerobicExercise)
                binding.tvEjercicioAerobio.visibility = View.GONE
            if (!practicant!!.crossfit)
                binding.tvCrossfit.visibility = View.GONE
            if (!practicant!!.yoga)
                binding.tvYoga.visibility = View.GONE
            if (!practicant!!.spinning)
                binding.tvSpinning.visibility = View.GONE
            if (!practicant!!.muscleTraining)
                binding.tvEntrenamientoMusculacion.visibility = View.GONE
            if (!practicant!!.pilates)
                binding.tvPilates.visibility = View.GONE
            if (practicant!!.other != "") {
                binding.tvOtro.visibility = View.VISIBLE
                binding.tvOtro.text = practicant!!.other
            }

        }
    }

    private fun loadResultsAndEvaluations() {
        if (isAthlete) {
            // resultados de atleta
            binding.tvResultAbd60.text = athlete!!.measureAbd60.toString()
            binding.tvResultPp.text = athlete!!.measurePp.toString()
            binding.tvResultPld.text = athlete!!.measurePld.toString()
            binding.tvResultPli.text = athlete!!.measurePli.toString()
            binding.tvResultIsmt.text = athlete!!.measureIsmt.toString()

            binding.tvResultCs.text = athlete!!.measureCs.toString()
            binding.tvResultCn.text = athlete!!.measureCn.toString()

            binding.tvResultIsocuad.text = athlete!!.measureIsocuad.toString()
            binding.tvResultPd.text = athlete!!.measurePd.toString()
            binding.tvResultCang.text = athlete!!.measureCang.toString()
            // evaluacion de atleta
            binding.tvEvalAbd60.text = athlete!!.evalAbd60
            binding.tvEvalPp.text = athlete!!.evalPp
            binding.tvEvalPld.text = athlete!!.evalPld
            binding.tvEvalPli.text = athlete!!.evalPli
            binding.tvEvalIsmt.text = athlete!!.evalIsmt

            binding.tvEvalCs.text = athlete!!.evalCs
            binding.tvEvalCn.text = athlete!!.evalCn

            binding.tvEvalIsocuad.text = athlete!!.evalIsocuad
            binding.tvEvalPd.text = athlete!!.evalPp
            binding.tvEvalCang.text = athlete!!.evalCang
        }
        else if (isPracticant) {
            // resultados de practicante
            binding.tvResultAbd60.text = practicant!!.measureAbd60.toString()
            binding.tvResultPp.text = practicant!!.measurePp.toString()
            binding.tvResultPld.text = practicant!!.measurePld.toString()
            binding.tvResultPli.text = practicant!!.measurePli.toString()
            binding.tvResultIsmt.text = practicant!!.measureIsmt.toString()

            binding.tvResultCs.text = practicant!!.measureCs.toString()
            binding.tvResultCn.text = practicant!!.measureCn.toString()

            binding.tvResultIsocuad.text = practicant!!.measureIsocuad.toString()
            binding.tvResultPd.text = practicant!!.measurePd.toString()
            binding.tvResultCang.text = practicant!!.measureCang.toString()
            // evaluacion de practicante
            binding.tvEvalAbd60.text = practicant!!.evalAbd60
            binding.tvEvalPp.text = practicant!!.evalPp
            binding.tvEvalPld.text = practicant!!.evalPld
            binding.tvEvalPli.text = practicant!!.evalPli
            binding.tvEvalIsmt.text = practicant!!.evalIsmt

            binding.tvEvalCs.text = practicant!!.evalCs
            binding.tvEvalCn.text = practicant!!.evalCn

            binding.tvEvalIsocuad.text = practicant!!.evalIsocuad
            binding.tvEvalPd.text = practicant!!.evalPp
            binding.tvEvalCang.text = practicant!!.evalCang
        }
    }

    override fun onResume() {
        super.onResume()
        if (isAthlete) {
            CoroutineScope(Dispatchers.IO).launch {
                athleteViewModel.getAthleteById(intent.extras!!.getInt("athlete_id"))
            }
        } else if (isPracticant) {
            CoroutineScope(Dispatchers.IO).launch {
                practicantViewModel.getPracticantById(intent.extras!!.getInt("practicant_id"))
            }
        }
    }

}