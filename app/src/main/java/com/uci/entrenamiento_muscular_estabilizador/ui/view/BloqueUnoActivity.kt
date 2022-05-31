package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityBloqueUnoBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BloqueUnoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBloqueUnoBinding

    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var athlete: AthleteEntity? = null
    private var practicant: PracticantEntity? = null
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false
    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBloqueUnoBinding.inflate(layoutInflater)
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
        binding.btnAbd60.setOnClickListener {
            if (binding.etValorAbd60.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measureAbd60 = binding.etValorAbd60.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }

                } else if (isPracticant) {
                    Toast.makeText(this, R.string.saved_result, Toast.LENGTH_SHORT)
                    practicant!!.measureAbd60 = binding.etValorAbd60.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorAbd60.hint = hint
                binding.etValorAbd60.setHintTextColor(Color.RED)
            }

        }
        binding.btnPld.setOnClickListener {
            if (binding.etValorPld.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measurePld = binding.etValorPld.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measurePld = binding.etValorPld.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorPld.hint = hint
                binding.etValorPld.setHintTextColor(Color.RED)
            }
        }
        binding.btnPli.setOnClickListener {
            if (binding.etValorPli.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measurePli = binding.etValorPli.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measurePli = binding.etValorPli.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorPli.hint = hint
                binding.etValorPli.setHintTextColor(Color.RED)
            }
        }
        binding.btnPp.setOnClickListener {
            if (binding.etValorPp.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measurePp = binding.etValorPp.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measurePp = binding.etValorPp.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorPp.hint = hint
                binding.etValorPp.setHintTextColor(Color.RED)
            }
        }
        binding.btnIsmt.setOnClickListener {
            if (binding.etValorIsmt.text.isNotEmpty()) {
                if (isAthlete) {
                    athlete!!.measureIsmt = binding.etValorIsmt.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else if (isPracticant) {
                    practicant!!.measureIsmt = binding.etValorIsmt.text.toString().toDouble()
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                        runOnUiThread {
                            Toast.makeText(context, R.string.saved_result, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                binding.etValorIsmt.hint = hint
                binding.etValorIsmt.setHintTextColor(Color.RED)
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
}