package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AthleteViewModel @Inject constructor(private val personDatabase: PersonDatabase) : ViewModel() {

    val athleteModel = MutableLiveData<List<AthleteEntity>>()

    suspend fun getAllAthlete() {
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
    }

    suspend fun addAthlete(person:PersonEntity):Long {
        val success = personDatabase.getPersonDao().insertPerson(person)
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
        return success
    }
}