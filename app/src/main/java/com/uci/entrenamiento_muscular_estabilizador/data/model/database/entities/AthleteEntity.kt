package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "athlete_table")
data class AthleteEntity(
    @ColumnInfo override val id: Int,
    override val fullName: String,
    override val gender: String,
    override val age: Int,
    override val height: Double,
    override val weight: Double,
    override val province: String,
    override val municipality: String,
    @ColumnInfo var sport: String? = null,
    @ColumnInfo var yearsInSport: Int = 0
) : PersonEntity(id, fullName, gender, age, height, weight, province, municipality)