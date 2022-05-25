package com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity

@Dao
interface AthleteDao {

    @Query("Select * from athlete_table")
    suspend fun getAllAthletes():List<AthleteEntity>

    @Query("Select * from athlete_table where person_id = :id")
    suspend fun getAthleteById(id:Int): AthleteEntity

    @Update(entity = AthleteEntity::class)
    suspend fun updateAthlete(athlete: AthleteEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(athletes:List<AthleteEntity>): List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insertAthlete(athlete : AthleteEntity):Long
}