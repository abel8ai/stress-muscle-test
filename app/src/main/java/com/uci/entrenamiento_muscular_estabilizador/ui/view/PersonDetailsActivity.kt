package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPersonDetailsBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.DialogAddAthleteBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.DialogAddPracticantBinding
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

        //actionbar - set back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_edit_person, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.ic_edit -> {
                if (isAthlete)
                    updateAthleteDialog()
                else if (isPracticant)
                    updatePracticantDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
            binding.llDeporte.visibility = View.GONE
            binding.llAnnosDeporte.visibility = View.GONE

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
                binding.llOtro.visibility = View.VISIBLE
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
        } else if (isPracticant) {
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

    private fun updatePracticantDialog() {
        val bindingForm: DialogAddPracticantBinding =
            DialogAddPracticantBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.genders)
        )
        val hint = resources.getString(R.string.mandatory_field)
        bindingForm.spinnerSexo.adapter = adapter
        // auto fill fields
        if (practicant!!.gender == "M")
            bindingForm.spinnerSexo.setSelection(0)
        else if (practicant!!.gender == "F")
            bindingForm.spinnerSexo.setSelection(1)
        val splitName = practicant!!.fullName.split(" ", ignoreCase = true, limit = 2)
        bindingForm.etNombre.setText(splitName[0])
        val apellidos = splitName[1]
        bindingForm.etApellidos.setText(apellidos)
        bindingForm.etEdad.setText(practicant!!.age.toString())
        bindingForm.etPeso.setText(practicant!!.weight.toString())
        bindingForm.etMunicipio.setText(practicant!!.municipality)
        bindingForm.etEstatura.setText(practicant!!.height.toString())
        bindingForm.etProvincia.setText(practicant!!.province)

        bindingForm.cbEjercicioAerobio.isChecked = practicant!!.aerobicExercise
        bindingForm.cbSpinning.isChecked = practicant!!.spinning
        bindingForm.cbEntrenamientoMusculacion.isChecked = practicant!!.muscleTraining
        bindingForm.cbYoga.isChecked = practicant!!.yoga
        bindingForm.cbPilates.isChecked = practicant!!.pilates
        bindingForm.cbCrossfit.isChecked = practicant!!.crossfit
        bindingForm.etOtro.setText(practicant!!.other)
        //-------------------------------
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_modify_practicant))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        val dialog = builder.show()
        bindingForm.btCancelar.setOnClickListener {
            dialog.dismiss()
        }
        bindingForm.btAdicionar.text = resources.getString(R.string.button_modify)
        bindingForm.btAdicionar.setOnClickListener {
            if (!validPracticantFields(bindingForm)) {
                if (bindingForm.etNombre.text.isEmpty()) {
                    bindingForm.etNombre.hint = hint
                    bindingForm.etNombre.setHintTextColor(Color.RED)
                }
                if (bindingForm.etApellidos.text.isEmpty()) {
                    bindingForm.etApellidos.hint = hint
                    bindingForm.etApellidos.setHintTextColor(Color.RED)
                }
                if (bindingForm.etEdad.text.isEmpty()) {
                    bindingForm.etEdad.hint = hint
                    bindingForm.etEdad.setHintTextColor(Color.RED)
                }
                if (bindingForm.etEstatura.text.isEmpty()) {
                    bindingForm.etEstatura.hint = hint
                    bindingForm.etEstatura.setHintTextColor(Color.RED)
                }
                if (bindingForm.etPeso.text.isEmpty()) {
                    bindingForm.etPeso.hint = hint
                    bindingForm.etPeso.setHintTextColor(Color.RED)
                }
                if (bindingForm.etProvincia.text.isEmpty()) {
                    bindingForm.etProvincia.hint = hint
                    bindingForm.etProvincia.setHintTextColor(Color.RED)
                }
                if (bindingForm.etMunicipio.text.isEmpty()) {
                    bindingForm.etMunicipio.hint = hint
                    bindingForm.etMunicipio.setHintTextColor(Color.RED)
                }

            } else {
                val name = bindingForm.etNombre.text.toString()
                val lastName = bindingForm.etApellidos.text.toString()
                val gender = bindingForm.spinnerSexo.selectedItem.toString()
                val age = bindingForm.etEdad.text.toString().toInt()
                val height = bindingForm.etEstatura.text.toString().toDouble()
                val weight = bindingForm.etPeso.text.toString().toDouble()
                val province = bindingForm.etProvincia.text.toString()
                val municip = bindingForm.etMunicipio.text.toString()
                val aerobics = bindingForm.cbEjercicioAerobio.isChecked
                val spinning = bindingForm.cbSpinning.isChecked
                val muscle = bindingForm.cbEntrenamientoMusculacion.isChecked
                val yoga = bindingForm.cbYoga.isChecked
                val pilates = bindingForm.cbPilates.isChecked
                val crossfit = bindingForm.cbCrossfit.isChecked
                val other = bindingForm.etOtro.text.toString()
                if (age < 7 || age > 55)
                    Toast.makeText(this, R.string.age_limit_55, Toast.LENGTH_SHORT)
                        .show()
                else {
                    practicant!!.fullName = "$name $lastName"
                    practicant!!.gender = gender
                    practicant!!.age = age
                    practicant!!.height = height
                    practicant!!.weight = weight
                    practicant!!.province = province
                    practicant!!.municipality = municip
                    practicant!!.aerobicExercise = aerobics
                    practicant!!.spinning = spinning
                    practicant!!.muscleTraining = muscle
                    practicant!!.crossfit = crossfit
                    practicant!!.yoga = yoga
                    practicant!!.pilates = pilates
                    practicant!!.other = other
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.updatePracticant(practicant!!)
                    }
                    dialog.dismiss()
                }
            }
        }
    }

    private fun updateAthleteDialog() {
        val bindingForm: DialogAddAthleteBinding =
            DialogAddAthleteBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.genders)
        )
        val hint = resources.getString(R.string.mandatory_field)
        bindingForm.spinnerSexo.adapter = adapter

        // auto fill fields
        if (athlete!!.gender == "M")
            bindingForm.spinnerSexo.setSelection(0)
        else if (athlete!!.gender == "F")
            bindingForm.spinnerSexo.setSelection(1)
        val splitName = athlete!!.fullName.split(" ", ignoreCase = true, limit = 2)
        bindingForm.etNombre.setText(splitName[0])
        val apellidos = splitName[1]
        bindingForm.etApellidos.setText(apellidos)
        bindingForm.etEdad.setText(athlete!!.age.toString())
        bindingForm.etPeso.setText(athlete!!.weight.toString())
        bindingForm.etMunicipio.setText(athlete!!.municipality)
        bindingForm.etAnnos.setText(athlete!!.yearsInSport.toString())
        bindingForm.etDeporte.setText(athlete!!.sport)
        bindingForm.etEstatura.setText(athlete!!.height.toString())
        bindingForm.etProvincia.setText(athlete!!.province)
        //-------------------------------
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_modify_athlete))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        val dialog = builder.show()
        bindingForm.btCancelar.setOnClickListener {
            dialog.dismiss()
        }
        bindingForm.btAdicionar.text = resources.getString(R.string.button_modify)
        bindingForm.btAdicionar.setOnClickListener {
            if (!validAthleteFields(bindingForm)) {
                if (bindingForm.etNombre.text.isEmpty()) {
                    bindingForm.etNombre.hint = hint
                    bindingForm.etNombre.setHintTextColor(Color.RED)
                }
                if (bindingForm.etApellidos.text.isEmpty()) {
                    bindingForm.etApellidos.hint = hint
                    bindingForm.etApellidos.setHintTextColor(Color.RED)
                }
                if (bindingForm.etEdad.text.isEmpty()) {
                    bindingForm.etEdad.hint = hint
                    bindingForm.etEdad.setHintTextColor(Color.RED)
                }
                if (bindingForm.etEstatura.text.isEmpty()) {
                    bindingForm.etEstatura.hint = hint
                    bindingForm.etEstatura.setHintTextColor(Color.RED)
                }
                if (bindingForm.etPeso.text.isEmpty()) {
                    bindingForm.etPeso.hint = hint
                    bindingForm.etPeso.setHintTextColor(Color.RED)
                }
                if (bindingForm.etProvincia.text.isEmpty()) {
                    bindingForm.etProvincia.hint = hint
                    bindingForm.etProvincia.setHintTextColor(Color.RED)
                }
                if (bindingForm.etMunicipio.text.isEmpty()) {
                    bindingForm.etMunicipio.hint = hint
                    bindingForm.etMunicipio.setHintTextColor(Color.RED)
                }
                if (bindingForm.etDeporte.text.isEmpty()) {
                    bindingForm.etDeporte.hint = hint
                    bindingForm.etDeporte.setHintTextColor(Color.RED)
                }
                if (bindingForm.etAnnos.text.isEmpty()) {
                    bindingForm.etAnnos.hint = hint
                    bindingForm.etAnnos.setHintTextColor(Color.RED)
                }
            } else {
                val name = bindingForm.etNombre.text.toString()
                val lastName = bindingForm.etApellidos.text.toString()
                val gender = bindingForm.spinnerSexo.selectedItem.toString()
                val age = bindingForm.etEdad.text.toString().toInt()
                val height = bindingForm.etEstatura.text.toString().toDouble()
                val weight = bindingForm.etPeso.text.toString().toDouble()
                val province = bindingForm.etProvincia.text.toString()
                val municip = bindingForm.etMunicipio.text.toString()
                val sport = bindingForm.etDeporte.text.toString()
                val yearsInSport = bindingForm.etAnnos.text.toString().toInt()
                if (age < 7 || age > 40)
                    Toast.makeText(this, R.string.age_limit_40, Toast.LENGTH_SHORT)
                        .show()
                else {
                    athlete!!.fullName = "$name $lastName"
                    athlete!!.gender = gender
                    athlete!!.age = age
                    athlete!!.height = height
                    athlete!!.weight = weight
                    athlete!!.province = province
                    athlete!!.municipality = municip
                    athlete!!.sport = sport
                    athlete!!.yearsInSport = yearsInSport
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.updateAthlete(athlete!!)
                    }
                    dialog.dismiss()
                }
            }
        }
    }

    private fun validPracticantFields(bindingForm: DialogAddPracticantBinding): Boolean {
        return !(bindingForm.etNombre.text.isEmpty() ||
                bindingForm.etApellidos.text.isEmpty() ||
                bindingForm.etEdad.text.isEmpty() ||
                bindingForm.etEstatura.text.isEmpty() ||
                bindingForm.etPeso.text.isEmpty() ||
                bindingForm.etProvincia.text.isEmpty() ||
                bindingForm.etMunicipio.text.isEmpty())
    }

    private fun validAthleteFields(bindingForm: DialogAddAthleteBinding): Boolean {
        return !(bindingForm.etNombre.text.isEmpty() ||
                bindingForm.etApellidos.text.isEmpty() ||
                bindingForm.etEdad.text.isEmpty() ||
                bindingForm.etEstatura.text.isEmpty() ||
                bindingForm.etPeso.text.isEmpty() ||
                bindingForm.etProvincia.text.isEmpty() ||
                bindingForm.etDeporte.text.isEmpty() ||
                bindingForm.etAnnos.text.isEmpty() ||
                bindingForm.etMunicipio.text.isEmpty())
    }

}