package com.example.naseem

import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.datasource.local.alert.AlertWeatherLocalDataSource
import com.example.naseem.data.datasource.local.fav.FavWeatherLocalDataSource
import com.example.naseem.data.datasource.remote.WeatherRemoteDataSource
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.entity.FavoriteEntity
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WeatherRepositoryTest {

    private lateinit var remoteDataSource: WeatherRemoteDataSource
    private lateinit var favLocalDataSource: FavWeatherLocalDataSource
    private lateinit var alertLocalDataSource: AlertWeatherLocalDataSource
    private lateinit var repository: WeatherRepository

    private val entity = FavoriteEntity("Cairo", "Cairo, Egypt", 30.0, 31.0)
    private val fakeApiKey = "test_api_key"

    @Before
    fun setup() {
        remoteDataSource = mockk()
        favLocalDataSource = mockk(relaxed = true)
        alertLocalDataSource = mockk(relaxed = true)

        // inject مباشرة بدون reflection
        repository = WeatherRepository(
            remoteDataSource = remoteDataSource,
            favLocalDataSource = favLocalDataSource,
            alertLocalDataSource = alertLocalDataSource,
            apiKey = fakeApiKey
        )
    }

    @Test
    fun getCurrentWeather_returnsSuccess() = runTest {
        val mockResponse = mockk<WeatherResponse>()
        coEvery {
            remoteDataSource.getCurrentWeather(30.0, 31.0, fakeApiKey)
        } returns ApiState.Success(mockResponse)

        val result = repository.getCurrentWeather(30.0, 31.0)

        assertTrue(result is ApiState.Success)
        assertEquals(mockResponse, (result as ApiState.Success).data)
    }

    @Test
    fun getCurrentWeather_returnsFailure() = runTest {
        coEvery {
            remoteDataSource.getCurrentWeather(any(), any(), any())
        } returns ApiState.Failure(Exception("Network error"))

        val result = repository.getCurrentWeather(0.0, 0.0)

        assertTrue(result is ApiState.Failure)
    }

    @Test
    fun insertWeatherToFav_callsLocalDataSource() = runTest {
        repository.insertWeatherToFav(entity)
        coVerify(exactly = 1) { favLocalDataSource.insertFavWeather(entity) }
    }

    @Test
    fun deleteWeatherFromFav_callsLocalDataSource() = runTest {
        repository.deleteWeatherFromFav(entity)
        coVerify(exactly = 1) { favLocalDataSource.deleteFavWeather(entity) }
    }

    @Test
    fun getAllFavWeather_returnsFlowFromLocal() = runTest {
        every { favLocalDataSource.getAllFavWeather() } returns flowOf(listOf(entity))
        val result = repository.getAllFavWeather().first()
        assertEquals(listOf(entity), result)
    }
}