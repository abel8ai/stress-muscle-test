package com.uci.entrenamiento_muscular_estabilizador.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.AthleteDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.PracticantDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity

@Database(entities = [AthleteEntity::class,PracticantEntity::class], version = 1,)
abstract class PersonDatabase:RoomDatabase() {

    abstract fun getAthleteDao(): AthleteDao
    abstract fun getPracticantDao(): PracticantDao
}