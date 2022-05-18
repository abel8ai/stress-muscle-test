package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.core.TestType
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType
import javax.inject.Inject

@HiltViewModel
class PracticantViewModel@Inject constructor(private val personDatabase: PersonDatabase, private val assetManager: AssetManager): ViewModel() {

    val practicantModel = MutableLiveData<List<PracticantEntity>>()

    suspend fun getAllPracticants() {
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
    }

    suspend fun addPractricant(practicant:PracticantEntity):Long {
        val success = personDatabase.getPracticantDao().insertPracticant(practicant)
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
        return success
    }
    fun evaluateTest(testType : TestType, age:Int, gender: String, measure:Double):String {
        // Obtain specific test row
        val baseRow = ((age-7)*20)+2
        var genderValue = 0
        if (gender == "Masculino")
            genderValue = 1
        else if (gender == "Femenino")
            genderValue = 2
        val testRow = (testType.ordinal+1) * genderValue
        val row = baseRow + testRow
        // Open excel from assets
        val myInput = assetManager.open("testResult.xls")
        val myFileSystem = POIFSFileSystem(myInput)
        val myWorkBook = HSSFWorkbook(myFileSystem)
        // Get sheet for practicants (0)
        val mySheet = myWorkBook.getSheetAt(0)
        // Set value in measure column (5)
        mySheet.getRow(row).createCell(5, CellType.NUMERIC).setCellValue(measure)
        // Get value in result column (6)
        val cell = mySheet.getRow(row).getCell(6)
        val evaluator = myWorkBook.creationHelper.createFormulaEvaluator()
        return evaluator.evaluate(cell).toString()
    }
    suspend fun createPracticantSheet(workbook : HSSFWorkbook){
        val practicantsheet = workbook.createSheet("Practicantes")
        val practicantList = personDatabase.getPracticantDao().getAllPracticants()
        var i = 0
        practicantList.forEach { practicant ->
            val row = practicantsheet.createRow(i)
            row.createCell(0).setCellValue(practicant.fullName)
            row.createCell(1).setCellValue(practicant.gender)
            row.createCell(2).setCellValue(practicant.age.toString())
            row.createCell(3).setCellValue(practicant.height)
            row.createCell(4).setCellValue(practicant.weight)
            row.createCell(5).setCellValue(practicant.province)
            row.createCell(6).setCellValue(practicant.municipality)

            row.createCell(7).setCellValue(practicant.aerobicExercise)
            row.createCell(8).setCellValue(practicant.spinning)
            row.createCell(9).setCellValue(practicant.muscleTraining)
            row.createCell(10).setCellValue(practicant.crossfit)
            row.createCell(11).setCellValue(practicant.yoga)
            row.createCell(12).setCellValue(practicant.pilates)
            row.createCell(13).setCellValue(practicant.other)

            row.createCell(14).setCellValue(practicant.measureAdb60)
            row.createCell(15).setCellValue(practicant.evalAdb60)
            row.createCell(16).setCellValue(practicant.measurePp)
            row.createCell(17).setCellValue(practicant.evalPp)
            row.createCell(18).setCellValue(practicant.measurePld)
            row.createCell(19).setCellValue(practicant.evalPld)
            row.createCell(20).setCellValue(practicant.measurePli)
            row.createCell(21).setCellValue(practicant.evalPli)
            row.createCell(22).setCellValue(practicant.measureIsmt)
            row.createCell(23).setCellValue(practicant.evalIsmt)
            row.createCell(24).setCellValue(practicant.measureCs)
            row.createCell(25).setCellValue(practicant.evalCs)
            row.createCell(26).setCellValue(practicant.measureCn)
            row.createCell(27).setCellValue(practicant.evalCn)
            row.createCell(28).setCellValue(practicant.measureIsocuad)
            row.createCell(29).setCellValue(practicant.evalIsocuad)
            row.createCell(30).setCellValue(practicant.measurePd)
            row.createCell(31).setCellValue(practicant.evalPd)

            i++
        }
    }
}