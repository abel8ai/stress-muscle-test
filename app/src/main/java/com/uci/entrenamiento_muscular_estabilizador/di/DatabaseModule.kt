package com.uci.entrenamiento_muscular_estabilizador.di

import android.content.Context
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.AthleteDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.PracticantDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideControlDao(appDatabase: PersonDatabase): AthleteDao {
        return appDatabase.getAthleteDao()
    }
    @Provides
    fun provideControladorDao(appDatabase: PersonDatabase): PracticantDao {
        return appDatabase.getPracticantDao()
    }
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): PersonDatabase {
        return Room.databaseBuilder(
            appContext,
            PersonDatabase::class.java,
            "database"
        ).build()
    }
}