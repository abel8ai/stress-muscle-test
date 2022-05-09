package com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "person_table")
abstract class PersonEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "person_id") open val id : Int = 0,
    @ColumnInfo(name = "full_name") open val fullName: String,
    @ColumnInfo(name = "gender") open val gender: String,
    @ColumnInfo(name = "age") open val age: Int,
    @ColumnInfo(name = "height") open val height: Double,
    @ColumnInfo(name = "weight") open val weight: Double,
    @ColumnInfo(name = "province") open val province: String,
    @ColumnInfo(name = "municipality") open val municipality: String
        )