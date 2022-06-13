package com.uci.entrenamiento_muscular_estabilizador.ui.view_model

import android.content.Context
import android.content.res.AssetManager
import android.os.Build.VERSION_CODES.Q
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.PersonDatabase
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.dao.AthleteDao
import com.uci.entrenamiento_muscular_estabilizador.data.model.database.entities.AthleteEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
//@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Q], manifest = "src/main/AndroidManifest.xml", packageName = "com.uci.entrenamiento_muscular_estabilizador")
internal class AthleteViewModelTest {

    @RelaxedMockK
    private lateinit var database: PersonDatabase
    private lateinit var assetsManager: AssetManager
    private lateinit var athleteViewModel: AthleteViewModel
    private lateinit var context: Context
    @RelaxedMockK
    private lateinit var athleteDao : AthleteDao


    @BeforeEach
    fun createDb() {
        MockKAnnotations.init(this)
        //context = ApplicationProvider.getApplicationContext()
        context = InstrumentationRegistry.getInstrumentation().context
        database = Room.inMemoryDatabaseBuilder(context, PersonDatabase::class.java).build()
        assetsManager = context.assets
        athleteViewModel = AthleteViewModel(database, assetsManager)
        athleteDao = database.getAthleteDao()
    }
    @Test
    fun whenDatabaseIsEmptyReturnsEmptyList() = runBlocking {
        // given
        coEvery { athleteDao.getAllAthletes() } returns null as MutableList<AthleteEntity>
        // when
        athleteViewModel.getAllAthletes()
        val result = athleteViewModel.athletesModel.getorAwaitValue()
        // then
        coVerify(exactly = 1) { athleteDao.getAllAthletes() }
        Truth.assertThat(result == mutableListOf<AthleteEntity>()).isTrue()
    }

}

