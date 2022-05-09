package com.uci.entrenamiento_muscular_estabilizador.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uci.entrenamiento_muscular_estabilizador.model.database.dao.PersonDao
import com.uci.entrenamiento_muscular_estabilizador.model.database.entities.PersonEntity

@Database(entities = [PersonEntity::class], version = 1,)
abstract class PersonDatabase:RoomDatabase() {

    abstract fun getPersonDao():PersonDao
}