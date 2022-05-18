package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.core.TestType
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType
import javax.inject.Inject

@HiltViewModel
class AthleteViewModel @Inject constructor(private val personDatabase: PersonDatabase, private val assetManager: AssetManager) : ViewModel() {

    val athleteModel = MutableLiveData<List<AthleteEntity>>()

    suspend fun getAllAthletes() {
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
    }

    suspend fun addAthlete(athlete:AthleteEntity):Long {
        val success = personDatabase.getAthleteDao().insertAthlete(athlete)
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
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
        // Get sheet for Athlete (1)
        val mySheet = myWorkBook.getSheetAt(1)
        // Set value in measure column (5)
        mySheet.getRow(row).createCell(5, CellType.NUMERIC).setCellValue(measure)
        // Get value in result column (6)
        val cell = mySheet.getRow(row).getCell(6)
        val evaluator = myWorkBook.creationHelper.createFormulaEvaluator()
        return evaluator.evaluate(cell).stringValue
    }

    suspend fun createAthleteSheet(workbook : HSSFWorkbook){
        val athletesheet = workbook.createSheet("Atletas")
        val athleteList = personDatabase.getAthleteDao().getAllAthletes()
        var i = 0
        athleteList.forEach { athlete ->
            val row = athletesheet.createRow(i)
            row.createCell(0).setCellValue(athlete.fullName)
            row.createCell(1).setCellValue(athlete.gender)
            row.createCell(2).setCellValue(athlete.age.toString())
            row.createCell(3).setCellValue(athlete.height)
            row.createCell(4).setCellValue(athlete.weight)
            row.createCell(5).setCellValue(athlete.province)
            row.createCell(6).setCellValue(athlete.municipality)

            row.createCell(7).setCellValue(athlete.sport)
            row.createCell(8).setCellValue(athlete.yearsInSport.toString())

            row.createCell(9).setCellValue(athlete.measureAdb60)
            row.createCell(10).setCellValue(athlete.evalAdb60)
            row.createCell(11).setCellValue(athlete.measurePp)
            row.createCell(12).setCellValue(athlete.evalPp)
            row.createCell(13).setCellValue(athlete.measurePld)
            row.createCell(14).setCellValue(athlete.evalPld)
            row.createCell(15).setCellValue(athlete.measurePli)
            row.createCell(16).setCellValue(athlete.evalPli)
            row.createCell(17).setCellValue(athlete.measureIsmt)
            row.createCell(18).setCellValue(athlete.evalIsmt)
            row.createCell(19).setCellValue(athlete.measureCs)
            row.createCell(20).setCellValue(athlete.evalCs)
            row.createCell(21).setCellValue(athlete.measureCn)
            row.createCell(22).setCellValue(athlete.evalCn)
            row.createCell(23).setCellValue(athlete.measureIsocuad)
            row.createCell(24).setCellValue(athlete.evalIsocuad)
            row.createCell(25).setCellValue(athlete.measurePd)
            row.createCell(26).setCellValue(athlete.evalPd)

            i++
        }
    }
}