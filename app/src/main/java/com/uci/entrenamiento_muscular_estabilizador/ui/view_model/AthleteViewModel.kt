package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.content.res.AssetManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.core.TestType
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.AthleteDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import javax.inject.Inject

@HiltViewModel
class AthleteViewModel @Inject constructor(private val athleteDao: AthleteDao, private val assetManager: AssetManager) : ViewModel() {

    val athletesModel = MutableLiveData<MutableList<AthleteEntity>>()
    val athleteModel = MutableLiveData<AthleteEntity>()

    suspend fun getAllAthletes() {
        athletesModel.postValue(athleteDao.getAllAthletes())
    }

    suspend fun getAthleteById(id:Int){
        athleteModel.postValue(athleteDao.getAthleteById(id))
    }

    suspend fun addAthlete(athlete:AthleteEntity):Long {
        val success = athleteDao.insertAthlete(athlete)
        athletesModel.postValue(athleteDao.getAllAthletes())
        return success
    }
    suspend fun updateAthlete(athlete: AthleteEntity){
        athleteDao.updateAthlete(athlete)
        athleteModel.postValue(athleteDao.getAthleteById(athlete.id!!))
    }

    suspend fun deleteAthlete(athlete: AthleteEntity){
        athleteDao.deleteAthlete(athlete)
        athletesModel.postValue(athleteDao.getAllAthletes())
    }

    fun evaluateTest(testType : TestType, athlete: AthleteEntity, measure:Double):String {
        // Obtain specific test row
        val baseRow = ((athlete.age-7)*20)
        var genderValue = 0
        if (athlete.gender == "M")
            genderValue = 1
        else if (athlete.gender == "F")
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

    suspend fun evaluateAthlete(athlete: AthleteEntity){
        athlete.evalAbd60 = evaluateTest(TestType.ADB60,athlete,athlete.measureAbd60)
        athlete.evalPp = evaluateTest(TestType.PP,athlete,athlete.measurePp)
        athlete.evalPld = evaluateTest(TestType.PLD,athlete,athlete.measurePld)
        athlete.evalPli = evaluateTest(TestType.PLI,athlete,athlete.measurePli)
        athlete.evalIsmt = evaluateTest(TestType.ISMT,athlete,athlete.measureIsmt)

        athlete.evalCs = evaluateTest(TestType.CS,athlete,athlete.measureCs)
        athlete.evalCn = evaluateTest(TestType.CN,athlete,athlete.measureCn)

        athlete.evalIsocuad = evaluateTest(TestType.ISOCUAD,athlete,athlete.measureIsocuad)
        athlete.evalPd = evaluateTest(TestType.PD,athlete,athlete.measurePd)
        athlete.evalCang = evaluateTest(TestType.CANG,athlete,athlete.measureCang)
        athleteDao.updateAthlete(athlete)
    }

    suspend fun createAthleteSheet(workbook : Workbook){
        val athletesheet = workbook.createSheet("Atletas")
        val athleteList = athleteDao.getAllAthletes()
        // Create Header
        val head = athletesheet.createRow(0)
        head.createCell(0).setCellValue("Nombre y Apellidos")
        head.createCell(1).setCellValue("Género")
        head.createCell(2).setCellValue("Edad")
        head.createCell(3).setCellValue("Estatura")
        head.createCell(4).setCellValue("Peso")
        head.createCell(5).setCellValue("Provincia")
        head.createCell(6).setCellValue("Municipio")

        head.createCell(7).setCellValue("Deporte")
        head.createCell(8).setCellValue("Años en el deporte")

        head.createCell(9).setCellValue("ADB60")
        head.createCell(10).setCellValue("Eval_ADB60")
        head.createCell(11).setCellValue("PP")
        head.createCell(12).setCellValue("Eval_PP")
        head.createCell(13).setCellValue("PLD")
        head.createCell(14).setCellValue("Eval_PLD")
        head.createCell(15).setCellValue("PLI")
        head.createCell(16).setCellValue("Eval_PLI")
        head.createCell(17).setCellValue("ISMT")
        head.createCell(18).setCellValue("Eval_")
        head.createCell(19).setCellValue("CS")
        head.createCell(20).setCellValue("Eval_CS")
        head.createCell(21).setCellValue("CN")
        head.createCell(22).setCellValue("Eval_CN")
        head.createCell(23).setCellValue("ISOCUAD")
        head.createCell(24).setCellValue("Eval_ISOCUAD")
        head.createCell(25).setCellValue("PD")
        head.createCell(26).setCellValue("Eval_PD")
        head.createCell(27).setCellValue("CANG")
        head.createCell(28).setCellValue("Eval_CANG")
        var i = 1
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

            row.createCell(9).setCellValue(athlete.measureAbd60)
            row.createCell(10).setCellValue(athlete.evalAbd60)
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
            row.createCell(27).setCellValue(athlete.measureCang)
            row.createCell(28).setCellValue(athlete.evalCang)

            i++
        }
    }
}