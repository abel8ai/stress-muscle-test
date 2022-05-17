package com.uci.entrenamiento_muscular_estabilizador.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.uci.entrenamiento_muscular_estabilizador.databinding.ActivityTestsBinding
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType


class TestsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        evaluateTest()
    }

    fun evaluateTest() {
        val myInput = assets.open("testResult.xls");
        val myFileSystem = POIFSFileSystem(myInput)
        val myWorkBook = HSSFWorkbook(myFileSystem)
        // Get the first sheet from workbook
        val mySheet = myWorkBook.getSheetAt(0)
        // We now need something to iterate through the cells.
        mySheet.getRow(3).createCell(5,CellType.NUMERIC).setCellValue(25.0)
        val cell = mySheet.getRow(3).getCell(6)
        val evaluator = myWorkBook.creationHelper.createFormulaEvaluator()
        val result = evaluator.evaluate(cell)
        binding.tv.text = result.stringValue
    }
}