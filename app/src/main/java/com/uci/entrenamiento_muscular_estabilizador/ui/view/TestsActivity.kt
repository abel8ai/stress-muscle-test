package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.core.TestType
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityTestsBinding
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.AthleteViewModel
import com.uci.entrenamiento_muscular_estabilizador.ui.view_model.PracticantViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType

@AndroidEntryPoint
class TestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestsBinding
    private val practicantViewModel: PracticantViewModel by viewModels()
    private val athleteViewModel: AthleteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val result = athleteViewModel.evaluateTest(TestType.ISOCUAD,27,"Femenino",189.0)
        binding.tv.text = result
    }

}