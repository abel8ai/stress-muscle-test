package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
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
import java.util.*

@AndroidEntryPoint
class PersonsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonsBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private lateinit var practicantAdapter: PracticantAdapter
    private lateinit var athleteAdapter: AthleteAdapter
    private var athletelist = mutableListOf<AthleteEntity>()
    private var practicantList = mutableListOf<PracticantEntity>()
    private var isAthlete: Boolean = false
    private var isPracticant: Boolean = false
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang", "es")
        setLocale(lang!!)
        binding = ActivityPersonsBinding.inflate(layoutInflater)
        supportActionBar!!.title = resources.getString(R.string.lista_personas)
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
        if (isAthlete) {
            athleteViewModel.athletesModel.observe(this, Observer {
                athletelist = it
                if (athletelist.isEmpty())
                    setVisible("iv")
                else {
                    setVisible("rv")
                    athleteAdapter = AthleteAdapter(athletelist, athleteViewModel)
                    binding.rvPersonList.adapter = athleteAdapter
                }

            })
        } else if (isPracticant) {
            practicantViewModel.practicantsModel.observe(this, Observer {
                practicantList = it
                if (practicantList.isEmpty())
                    setVisible("iv")
                else {
                    setVisible("rv")
                    practicantAdapter = PracticantAdapter(practicantList, practicantViewModel)
                    binding.rvPersonList.adapter = practicantAdapter
                }

            })
        }

        //actionbar - set back button
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecycleView() {
        binding.rvPersonList.layoutManager = LinearLayoutManager(this)
        if (isAthlete) {
            if (athletelist.isEmpty())
                setVisible("iv")
            else {
                setVisible("rv")
                athleteAdapter = AthleteAdapter(athletelist, athleteViewModel)
                binding.rvPersonList.adapter = athleteAdapter
            }

        } else if (isPracticant) {
            if (practicantList.isEmpty())
                setVisible("iv")
            else {
                setVisible("rv")
                practicantAdapter = PracticantAdapter(practicantList, practicantViewModel)
                binding.rvPersonList.adapter = practicantAdapter
            }

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
        val hint = resources.getString(R.string.mandatory_field)
        bindingForm.spinnerSexo.adapter = adapter
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_add_practicant))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        val dialog = builder.show()
        bindingForm.btCancelar.setOnClickListener {
            dialog.dismiss()
        }
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
                var gender = ""
                if (bindingForm.spinnerSexo.selectedItemId == 0L)
                    gender = "M"
                else
                    gender = "F"
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
                    val practicant = PracticantEntity(
                        null,
                        "$name $lastName",
                        gender,
                        age,
                        height,
                        weight,
                        province,
                        municip,
                        aerobics,
                        spinning,
                        muscle,
                        crossfit,
                        yoga,
                        pilates,
                        other
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        practicantViewModel.addPractricant(practicant)
                    }
                    dialog.dismiss()
                }
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
        val hint = resources.getString(R.string.mandatory_field)
        bindingForm.spinnerSexo.adapter = adapter

        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.dialog_add_athlete))
        builder.setView(bindingForm.root)
        builder.setCancelable(false)
        builder.create()
        val dialog = builder.show()
        bindingForm.btCancelar.setOnClickListener {
            dialog.dismiss()
        }
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
                var gender = ""
                if (bindingForm.spinnerSexo.selectedItemId == 0L)
                    gender = "M"
                else
                    gender = "F"
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
                    val athlete = AthleteEntity(
                        null, "$name $lastName", gender, age, height, weight,
                        province, municip, sport, yearsInSport
                    )
                    CoroutineScope(Dispatchers.IO).launch {
                        athleteViewModel.addAthlete(athlete)
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

    private fun setVisible(view: String) {
        if (view == "rv") {
            binding.rvPersonList.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.GONE
            binding.ivEmptyList.visibility = View.GONE
        } else if (view == "iv") {
            binding.rvPersonList.visibility = View.GONE
            binding.ivEmptyList.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
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