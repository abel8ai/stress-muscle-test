package com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity

@Dao
interface PersonDao {

    @Query("Select * from person_table")
    fun getAllPersons():List<PersonEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(persons:List<PersonEntity>)
}