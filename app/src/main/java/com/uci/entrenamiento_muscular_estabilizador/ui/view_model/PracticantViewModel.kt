package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PracticantEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PracticantViewModel@Inject constructor(private val personDatabase: PersonDatabase): ViewModel() {

    val practicantModel = MutableLiveData<List<PracticantEntity>>()

    suspend fun getAllPracticants() {
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
    }

    suspend fun addPractricant(practicant:PracticantEntity):Long {
        val success = personDatabase.getPracticantDao().insertPracticant(practicant)
        practicantModel.postValue(personDatabase.getPracticantDao().getAllPracticants())
        return success
    }
}