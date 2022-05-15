package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.annotation.Nullable

@Entity(tableName = "practicant_table")
data class PracticantEntity(
    @Nullable
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id") val id : Int?,
    @ColumnInfo(name = "full_name") val fullName: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "age") val age: Int,
    @ColumnInfo(name = "height") val height: Double,
    @ColumnInfo(name = "weight") val weight: Double,
    @ColumnInfo(name = "province") val province: String,
    @ColumnInfo(name = "municipality") val municipality: String,
    @ColumnInfo(name = "aerobic_exercise") var aerobicExercise: Boolean,
    @ColumnInfo(name = "spinning") var spinning: Boolean,
    @ColumnInfo(name = "muscle_training") var muscleTraining: Boolean,
    @ColumnInfo(name = "crossfit") var crossfit: Boolean,
    @ColumnInfo(name = "yoga") var yoga: Boolean,
    @ColumnInfo(name = "pilates") var pilates: Boolean,
    @ColumnInfo(name = "other") var other: String,
)