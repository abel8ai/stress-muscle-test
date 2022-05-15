package com.uci.entrenamiento_muscular_estabilizador.di

import android.app.Application
import androidx.room.Room
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(appContext:Application):PersonDatabase{
        return Room.databaseBuilder(appContext, PersonDatabase::class.java,"database").build()
    }
}