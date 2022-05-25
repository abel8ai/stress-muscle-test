package com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity

@Dao
interface PracticantDao {

    @Query("Select * from practicant_table")
    suspend fun getAllPracticants():List<PracticantEntity>

    @Query("Select * from practicant_table where person_id = :id")
    suspend fun getPracticantById(id:Int):PracticantEntity

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(practicants:List<PracticantEntity>): List<Long>

    @Insert(onConflict = REPLACE)
    suspend fun insertPracticant(practicant : PracticantEntity):Long
}