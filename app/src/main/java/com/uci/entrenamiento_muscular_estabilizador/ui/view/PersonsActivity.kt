package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityPersonsBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.DialogAddAthleteBinding
import com.uci.entrenamiento_muscular_estabilizador.databinding.DialogAddPracticantBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters.AthleteAdapter
import com.uci.entrenamiento_muscular_estabilizador.ui.view.adapters.PracticantAdapter
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PersonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonsBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private lateinit var practicantAdapter: PracticantAdapter
    private lateinit var athleteAdapter: AthleteAdapter
    private val athletelist = emptyList<AthleteEntity>()
    private val practicantList = emptyList<PracticantEntity>()
    private var isAthlete : Boolean = false
    private var isPracticant : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        isAthlete = intent.extras!!.getString("person_type").equals("athlete")
        isPracticant = intent.extras!!.getString("person_type").equals("practicant")

        //initialize viewmodels
        if (isAthlete)
            athleteViewModel
        else if (isPracticant)
            practicantViewModel
        initRecycleView()
        loadData()
        binding.favAddPerson.setOnClickListener {
            if (isAthlete)
                addAthleteDialog()
            else if (isPracticant)
                addPracticantDialog()
        }
        if (isAthlete){
            athleteViewModel.athleteModel.observe(this, Observer {
                athleteAdapter = AthleteAdapter(it)
                binding.rvPersonList.adapter = athleteAdapter
            })
        }
        else if (isPracticant){
            practicantViewModel.practicantModel.observe(this, Observer {
                practicantAdapter = PracticantAdapter(it)
                binding.rvPersonList.adapter = practicantAdapter
            })
        }

    }

    private fun initRecycleView() {
        binding.rvPersonList.layoutManager = LinearLayoutManager(this)
        if (isAthlete){
            athleteAdapter = AthleteAdapter(athletelist)
            binding.rvPersonList.adapter = athleteAdapter
        }
        else if (isPracticant){
            practicantAdapter = PracticantAdapter(practicantList)
            binding.rvPersonList.adapter = practicantAdapter
        }
    }

    private fun loadData() {
        if (isAthlete) {
            CoroutineScope(Dispatchers.IO).launch {
                athleteViewModel.getAllAthletes()
            }
        } else if (isPracticant) {
            CoroutineScope(Dispatchers.IO).launch {
                practicantViewModel.getAllPracticants()
            }
        }
    }

    private fun addPracticantDialog() {
        val bindingForm: DialogAddPracticantBinding =
            DialogAddPracticantBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.genders)
        )
        bindingForm.spinnerSexo.adapter = adapter

        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_add_practicant))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        builder.setNegativeButton(resources.getString(R.string.button_cancel)) { dialog, which ->
            dialog.dismiss()
        }
        builder.setPositiveButton(resources.getString(R.string.button_add)) { dialog, which ->
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
            val practicant = PracticantEntity(
                null, "$name $lastName", gender, age, height, weight,
                province, municip,aerobics,spinning,muscle,crossfit,yoga,pilates,other
            )
            CoroutineScope(Dispatchers.IO).launch {
                practicantViewModel.addPractricant(practicant)
            }
        }
    }
    private fun addAthleteDialog() {
        val bindingForm: DialogAddAthleteBinding =
            DialogAddAthleteBinding.inflate(layoutInflater)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.genders)
        )
        bindingForm.spinnerSexo.adapter = adapter

        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_add_athlete))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        builder.setNegativeButton(resources.getString(R.string.button_cancel)) { dialog, which ->
            dialog.dismiss()
        }
        builder.setPositiveButton(resources.getString(R.string.button_add)) { dialog, which ->
            val name = bindingForm.etNombre.text.toString()
            val lastName = bindingForm.etApellidos.text.toString()
            val gender = bindingForm.spinnerSexo.selectedItem.toString()
            val age = bindingForm.etEdad.text.toString().toInt()
            val height = bindingForm.etEstatura.text.toString().toDouble()
            val weight = bindingForm.etPeso.text.toString().toDouble()
            val province = bindingForm.etProvincia.text.toString()
            val municip = bindingForm.etMunicipio.text.toString()
            val sport = bindingForm.etDeporte.text.toString()
            val yearsInSport = bindingForm.etAnnos.toString().toInt()

            val athlete = AthleteEntity(
                null, "$name $lastName", gender, age, height, weight,
                province, municip,sport, yearsInSport
            )
            CoroutineScope(Dispatchers.IO).launch {
                athleteViewModel.addAthlete(athlete)
            }
        }
    }
}