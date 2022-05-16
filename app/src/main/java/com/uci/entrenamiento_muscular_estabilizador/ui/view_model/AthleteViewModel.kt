package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AthleteViewModel @Inject constructor(private val personDatabase: PersonDatabase) : ViewModel() {

    val athleteModel = MutableLiveData<List<AthleteEntity>>()

    suspend fun getAllAthletes() {
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
    }

    suspend fun addAthlete(athlete:AthleteEntity):Long {
        val success = personDatabase.getAthleteDao().insertAthlete(athlete)
        athleteModel.postValue(personDatabase.getAthleteDao().getAllAthletes())
        return success
    }
}