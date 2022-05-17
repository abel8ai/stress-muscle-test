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
    @ColumnInfo(name = "meassure_adb60") var meassureAdb60: Double? = 0.0
    @ColumnInfo(name = "meassure_pp") var meassurePp: Double? = 0.0
    @ColumnInfo(name = "meassure_pld") var meassurePld: Double? = 0.0
    @ColumnInfo(name = "meassure_pli") var meassurePli: Double? = 0.0
    @ColumnInfo(name = "meassure_ismt") var meassureIsmt: Double? = 0.0
    @ColumnInfo(name = "meassure_cs") var meassureCs: Double? = 0.0
    @ColumnInfo(name = "meassure_cn") var meassureCn: Double? = 0.0
    @ColumnInfo(name = "meassure_isocuad") var meassureIsocuad: Double? = 0.0
    @ColumnInfo(name = "meassure_pd") var meassurePd: Double? = 0.0

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