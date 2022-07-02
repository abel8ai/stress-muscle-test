package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.content.Context
import android.content.res.AssetManager
import android.os.Build.VERSION_CODES.Q
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.AthleteDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Q], manifest = "src/main/AndroidManifest.xml", packageName = "com.uci.entrenamiento_muscular_estabilizador")
internal class AthleteViewModelTest {

    @MockK
    private lateinit var database: PersonDatabase
    private lateinit var assetsManager: AssetManager
    private lateinit var athleteViewModel: AthleteViewModel
    private lateinit var context: Context
    @MockK
    private lateinit var athleteDao : AthleteDao


    @Before
    fun createDb() {
        MockKAnnotations.init(this)
        context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, PersonDatabase::class.java).build()
        assetsManager = context.assets
        athleteViewModel = AthleteViewModel(database, assetsManager)
        athleteDao = mockk()
    }
    @Test
    fun whenDatabaseIsEmptyReturnsEmptyList() = runBlocking {
        // given
        coEvery {athleteDao.getAllAthletes()} returns mutableListOf()
        // when
        athleteViewModel.getAllAthletes()
        val result = athleteViewModel.athletesModel.getorAwaitValue()
        // then
        coVerify(exactly = 1) { athleteDao.getAllAthletes() }
        Truth.assertThat(result == mutableListOf<AthleteEntity>()).isTrue()
    }

}

