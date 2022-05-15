package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.PersonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(val personDatabase: PersonDatabase) : ViewModel() {

    val personModel = MutableLiveData<List<PersonEntity>>()
    //private val personDatabase = Room.databaseBuilder(appContext, PersonDatabase::class.java, "database").build()

    suspend fun getAllPersons() {
        personModel.postValue(personDatabase.getPersonDao().getAllPersons())
    }

    suspend fun addPerson(person:PersonEntity):Long {
        val success = personDatabase.getPersonDao().insertPerson(person)
        personModel.postValue(personDatabase.getPersonDao().getAllPersons())
        return success
    }
}