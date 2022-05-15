package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
)