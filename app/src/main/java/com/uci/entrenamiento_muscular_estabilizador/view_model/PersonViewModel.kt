package com.uci.entrenamiento_muscular_estabilizador.view_model
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.model.database.entities.PersonEntity


class PersonViewModel(val context: Context) : ViewModel() {
    val personModel = MutableLiveData<List<PersonEntity>>()

    fun getAllPersons(){
        val dbBuilder = Room.databaseBuilder(context,PersonDatabase::class.java,"database").build()
        personModel.postValue(dbBuilder.getPersonDao().getAllPersons())
    }
}