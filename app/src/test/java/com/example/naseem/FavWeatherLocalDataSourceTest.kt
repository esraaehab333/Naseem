package com.example.naseem

import com.example.naseem.data.datasource.local.fav.FavWeatherDao
import com.example.naseem.data.datasource.local.fav.FavWeatherLocalDataSource
import com.example.naseem.data.entity.FavoriteEntity
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavWeatherLocalDataSourceTest {

    private lateinit var dao: FavWeatherDao
    private lateinit var localDataSource: FavWeatherLocalDataSource

    private val entity = FavoriteEntity("Cairo", "Cairo, Egypt", 30.0, 31.0)

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        localDataSource = FavWeatherLocalDataSource(dao)  // ← inject مباشرة، بدون reflection
    }

    @Test
    fun insertFavWeather_callsDaoInsert() = runTest {
        localDataSource.insertFavWeather(entity)
        coVerify(exactly = 1) { dao.insertFavWeather(entity) }
    }

    @Test
    fun deleteFavWeather_callsDaoDelete() = runTest {
        localDataSource.deleteFavWeather(entity)
        coVerify(exactly = 1) { dao.deleteFavWeather(entity) }
    }

    @Test
    fun getAllFavWeather_returnsFlowFromDao() = runTest {
        every { dao.getAllFavWeathers() } returns flowOf(listOf(entity))
        val result = localDataSource.getAllFavWeather().first()
        assertEquals(listOf(entity), result)
    }
}