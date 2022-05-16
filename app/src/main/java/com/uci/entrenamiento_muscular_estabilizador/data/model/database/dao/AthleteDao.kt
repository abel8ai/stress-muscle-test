package com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity

@Dao
interface AthleteDao {

    @Query("Select * from athlete_table")
    suspend fun getAllAthletes():List<AthleteEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(athletes:List<AthleteEntity>): List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insertAthlete(athlete : AthleteEntity):Long
}