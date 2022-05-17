package com.uci.entrenamiento_muscular_estabilizador.di

import android.app.Application
import android.content.res.AssetManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AssetsModule {
    @Provides
    @Singleton
    fun provideAssets(appContext: Application): AssetManager {
        return appContext.assets
    }
}