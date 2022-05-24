package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkWritePermission()
        practicantViewModel
        athleteViewModel
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
        binding.btExport.setOnClickListener {
            exportExcel()
        }
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.seleccion_idioma -> {
                return true
            }
            R.id.ic_informacion -> {
                return true
            }
            R.id.excel_export -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}