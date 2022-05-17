package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import javax.annotation.Nullable

@Entity(tableName = "athlete_table")
data class AthleteEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id") val id : Int?,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "height") val height: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "province") val province: String,
    @ColumnInfo(name = "municipality") val municipality: String,
    @ColumnInfo(name = "sport") var sport: String? = null,
    @ColumnInfo(name = "years_in_sport") var yearsInSport: Int
):Serializable {
    @ColumnInfo(name = "measure_adb60") var measureAdb60: Double? = 0.0
    @ColumnInfo(name = "measure_pp") var measurePp: Double? = 0.0
    @ColumnInfo(name = "measure_pld") var measurePld: Double? = 0.0
    @ColumnInfo(name = "measure_pli") var measurePli: Double? = 0.0
    @ColumnInfo(name = "measure_ismt") var measureIsmt: Double? = 0.0
    @ColumnInfo(name = "measure_cs") var measureCs: Double? = 0.0
    @ColumnInfo(name = "measure_cn") var measureCn: Double? = 0.0
    @ColumnInfo(name = "measure_isocuad") var measureIsocuad: Double? = 0.0
    @ColumnInfo(name = "measure_pd") var measurePd: Double? = 0.0

    @ColumnInfo(name = "eval_adb60") var evalAdb60: String? = ""
    @ColumnInfo(name = "eval_pp") var evalPp: String? = ""
    @ColumnInfo(name = "eval_pld") var evalPld: String? = ""
    @ColumnInfo(name = "eval_pli") var evalPli: String? = ""
    @ColumnInfo(name = "eval_ismt") var evalIsmt: String? = ""
    @ColumnInfo(name = "eval_cs") var evalCs: String? = ""
    @ColumnInfo(name = "eval_cn") var evalCn: String? = ""
    @ColumnInfo(name = "eval_isocuad") var evalIsocuad: String? = ""
    @ColumnInfo(name = "eval_pd") var evalPd: String? = ""
}