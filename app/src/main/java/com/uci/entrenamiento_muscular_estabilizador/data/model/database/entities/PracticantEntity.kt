package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity

@Entity(tableName = "practicant_table")
data class PracticantEntity(
    @ColumnInfo override val id: Int,
    override val fullName: String,
    override val gender: String,
    override val age: Int,
    override val height: Double,
    override val weight: Double,
    override val province: String,
    override val municipality: String,
    @ColumnInfo var aerobicExcercise: Boolean,
    @ColumnInfo var spinning: Boolean,
    @ColumnInfo var muscleTrainnig: Boolean,
    @ColumnInfo var crossfit: Boolean,
    @ColumnInfo var yoga: Boolean,
    @ColumnInfo var pilates: Boolean,
    @ColumnInfo var other: String,
) : PersonEntity(id, fullName, gender, age, height, weight, province, municipality)