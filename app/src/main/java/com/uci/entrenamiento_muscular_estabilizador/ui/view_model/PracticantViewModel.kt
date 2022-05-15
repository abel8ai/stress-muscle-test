package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticantViewModel@Inject constructor(private val personDatabase: PersonDatabase): ViewModel() {

    val practicantModel = MutableLiveData<List<PracticantEntity>>()

    suspend fun getAllPracticants() {
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
    }

    suspend fun addPractricant(person:PersonEntity):Long {
        val success = personDatabase.getPersonDao().insertPerson(person)
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
        return success
    }
}