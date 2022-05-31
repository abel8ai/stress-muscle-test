package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.core.TestType
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import javax.inject.Inject

@HiltViewModel
class PracticantViewModel@Inject constructor(private val personDatabase: PersonDatabase, private val assetManager: AssetManager): ViewModel() {

    val practicantsModel = MutableLiveData<MutableList<PracticantEntity>>()
    val practicantModel = MutableLiveData<PracticantEntity>()

    suspend fun getAllPracticants() {
        practicantsModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
    }

    suspend fun getPracticantById(id:Int){
        practicantModel.postValue(personDatabase.getPracticantDao().getPracticantById(id))
    }

    suspend fun addPractricant(practicant:PracticantEntity):Long {
        val success = personDatabase.getPracticantDao().insertPracticant(practicant)
        practicantsModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
        return success
    }
    suspend fun updatePracticant(practicant: PracticantEntity){
        personDatabase.getPracticantDao().updatePracticant(practicant)
        practicantModel.postValue(personDatabase.getPracticantDao().getPracticantById(practicant.id!!))
    }

    suspend fun deletePracticant(practicant: PracticantEntity){
        personDatabase.getPracticantDao().deletePracticant(practicant)
        practicantsModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
    }

    fun evaluateTest(testType : TestType, practicant: PracticantEntity, measure:Double):String {
        // Obtain specific test row
        val baseRow = ((practicant.age-7)*20)
        var testRow = 0
        if (practicant.gender == "Masculino")
            testRow = (testType.ordinal+1)*2-1
        else if (practicant.gender == "Femenino")
            testRow = (testType.ordinal+1)*2
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
        return evaluator.evaluate(cell).stringValue
    }

    suspend fun evaluatePracticant(practicant: PracticantEntity){
        practicant.evalAbd60 = evaluateTest(TestType.ADB60,practicant,practicant.measureAbd60)
        practicant.evalPp = evaluateTest(TestType.PP,practicant,practicant.measurePp)
        practicant.evalPld = evaluateTest(TestType.PLD,practicant,practicant.measurePld)
        practicant.evalPli = evaluateTest(TestType.PLI,practicant,practicant.measurePli)
        practicant.evalIsmt = evaluateTest(TestType.ISMT,practicant,practicant.measureIsmt)

        practicant.evalCs = evaluateTest(TestType.CS,practicant,practicant.measureCs)
        practicant.evalCn = evaluateTest(TestType.CN,practicant,practicant.measureCn)

        practicant.evalIsocuad = evaluateTest(TestType.ISOCUAD,practicant,practicant.measureIsocuad)
        practicant.evalPd = evaluateTest(TestType.PD,practicant,practicant.measurePd)
        practicant.evalCang = evaluateTest(TestType.PD,practicant,practicant.measureCang)
        personDatabase.getPracticantDao().updatePracticant(practicant)
    }

    suspend fun createPracticantSheet(workbook : Workbook){
        val practicantsheet = workbook.createSheet("Practicantes")
        val practicantList = personDatabase.getPracticantDao().getAllPracticants()
        // Create Header
        val head = practicantsheet.createRow(0)
        head.createCell(0).setCellValue("Nombre y Apellidos")
        head.createCell(1).setCellValue("Género")
        head.createCell(2).setCellValue("Edad")
        head.createCell(3).setCellValue("Estatura")
        head.createCell(4).setCellValue("Peso")
        head.createCell(5).setCellValue("Provincia")
        head.createCell(6).setCellValue("Municipio")

        head.createCell(7).setCellValue("Ejercicio Aerobio")
        head.createCell(8).setCellValue("Spinning")
        head.createCell(9).setCellValue("Entrenamiento muscular")
        head.createCell(10).setCellValue("Crossfit")
        head.createCell(11).setCellValue("Yoga")
        head.createCell(12).setCellValue("Pilates")
        head.createCell(13).setCellValue("Otro")

        head.createCell(14).setCellValue("ADB60")
        head.createCell(15).setCellValue("Eval_ADB60")
        head.createCell(16).setCellValue("PP")
        head.createCell(17).setCellValue("Eval_PP")
        head.createCell(18).setCellValue("PLD")
        head.createCell(19).setCellValue("Eval_PLD")
        head.createCell(20).setCellValue("PLI")
        head.createCell(21).setCellValue("Eval_PLI")
        head.createCell(22).setCellValue("ISMT")
        head.createCell(23).setCellValue("Eval_")
        head.createCell(24).setCellValue("CS")
        head.createCell(25).setCellValue("Eval_CS")
        head.createCell(26).setCellValue("CN")
        head.createCell(27).setCellValue("Eval_CN")
        head.createCell(28).setCellValue("ISOCUAD")
        head.createCell(29).setCellValue("Eval_ISOCUAD")
        head.createCell(30).setCellValue("PD")
        head.createCell(31).setCellValue("Eval_PD")
        head.createCell(32).setCellValue("CANG")
        head.createCell(33).setCellValue("Eval_CANG")

        var i = 1
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

            row.createCell(14).setCellValue(practicant.measureAbd60)
            row.createCell(15).setCellValue(practicant.evalAbd60)
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
            row.createCell(32).setCellValue(practicant.measureCang)
            row.createCell(33).setCellValue(practicant.evalCang)

            i++
        }
    }
}