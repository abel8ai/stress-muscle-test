package com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity

@Dao
interface PracticantDao {

    @Query("Select * from practicant_table")
    suspend fun getAllPracticants():MutableList<PracticantEntity>

    @Query("Select * from practicant_table where person_id = :id")
    suspend fun getPracticantById(id:Int):PracticantEntity

    @Update(entity = PracticantEntity::class)
    suspend fun updatePracticant(practicant: PracticantEntity)

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(practicants:List<PracticantEntity>): List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insertPracticant(practicant : PracticantEntity):Long

    @Delete
    suspend fun deletePracticant(practicant: PracticantEntity)
}