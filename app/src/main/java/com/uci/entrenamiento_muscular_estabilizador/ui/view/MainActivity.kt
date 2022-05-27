package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uci.entrenamiento_muscular_estabilizador.R
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityMainBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.File
import java.io.FileOutputStream
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    private var writingPermissionGranted:Boolean = false
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 334533
    private lateinit var menu:Menu
    private lateinit var sharedPref:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getSharedPreferences("lang", Context.MODE_PRIVATE)
        val lang = sharedPref.getString("lang","es")
        setLocale(lang!!)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkWritePermission()
        practicantViewModel
        athleteViewModel

        binding.tvLeerMasNuestraHistoria.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
        binding.btPracticantList.setOnClickListener {
            val intent = Intent(this,PersonsActivity::class.java)
            intent.putExtra("person_type","practicant")
            startActivity(intent)
        }
        binding.btAthleteList.setOnClickListener {
            val intent = Intent(this,PersonsActivity::class.java)
            intent.putExtra("person_type","athlete")
            startActivity(intent)
        }
        getWritingPermission()
    }
    fun exportExcel(){
        CoroutineScope(Dispatchers.IO).launch {
            val workbook = HSSFWorkbook()
            athleteViewModel.createAthleteSheet(workbook)
            practicantViewModel.createPracticantSheet(workbook)
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            val out = FileOutputStream(File("$path/results.xls"))
            workbook.write(out)
            out.close()
            workbook.close()
        }

    }
    private fun checkWritePermission() {
        val permissionCheck = ContextCompat.checkSelfPermission(
            this, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                ), 225
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        this.menu = menu
        val lang = sharedPref.getString("lang","es")
        if (lang == "es"){
            menu.getItem(0).subMenu.getItem(0).isChecked = true
            menu.getItem(0).subMenu.getItem(1).isChecked = false
        }
        else if (lang == "en"){
            menu.getItem(0).subMenu.getItem(0).isChecked = false
            menu.getItem(0).subMenu.getItem(1).isChecked = true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.seleccion_idioma -> {
                return true
            }
            R.id.ic_bibliografia -> {
                    val intent = Intent(this,BibliographyActivity::class.java)
                    startActivity(intent)
                return true
            }
            R.id.excel_export -> {
                exportExcel()
                return true
            }
            R.id.idioma_es->{
                item.isChecked = true
                menu.getItem(0).subMenu.getItem(1).isChecked = false
                sharedPref.edit().putString("lang","es").commit()
                setLocale("es")
                recreate()
            }
            R.id.idioma_en ->{
                item.isChecked = true
                menu.getItem(0).subMenu.getItem(0).isChecked = false
                sharedPref.edit().putString("lang","en").commit()
                setLocale("en")
                recreate()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = Locale(language)
        resources.updateConfiguration(config,resources.displayMetrics)
        onConfigurationChanged(config)
    }

    private fun getWritingPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            writingPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        writingPermissionGranted = false
        when (requestCode) {
            REQUEST_WRITE_EXTERNAL_STORAGE -> {

                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    writingPermissionGranted = true
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

}