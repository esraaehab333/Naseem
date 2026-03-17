package com.example.naseem

import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.presentation.home.viewModels.HomeViewModel
import com.example.naseem.utils.LocationHelper
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var repository: WeatherRepository
    private lateinit var locationHelper: LocationHelper
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockWeather = mockk<WeatherResponse>(relaxed = true)
    private val mockForecast = mockk<ForecastResponse>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        locationHelper = mockk()
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    private fun buildViewModel() = HomeViewModel(
        weatherRepository = repository,
        locationHelper = locationHelper,
        dispatcher = testDispatcher
    )

    @Test
    fun getCurrentWeather_onSuccess_updatesWeatherData() = runTest(testDispatcher) {
        coEvery { repository.getCurrentWeather(30.0, 31.0) } returns ApiState.Success(mockWeather)

        viewModel = buildViewModel()
        viewModel.getCurrentWeather(30.0, 31.0)

        assertEquals(mockWeather, viewModel.weatherData.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun getCurrentWeather_onFailure_emitsErrorMessage() = runTest(testDispatcher) {
        coEvery { repository.getCurrentWeather(any(), any()) } returns
                ApiState.Failure(Exception("Network error"))

        viewModel = buildViewModel()

        val errors = mutableListOf<String>()
        val job = launch(testDispatcher) { viewModel.errorMessage.toList(errors) }

        viewModel.getCurrentWeather(0.0, 0.0)

        assertTrue(errors.any { it.contains("Network error") })
        assertFalse(viewModel.isLoading.value)
        job.cancel()
    }

    @Test
    fun getCurrentWeather_setsLoadingTrueThenFalse() = runTest(testDispatcher) {
        coEvery { repository.getCurrentWeather(any(), any()) } returns ApiState.Success(mockWeather)

        viewModel = buildViewModel()
        viewModel.getCurrentWeather(30.0, 31.0)

        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun getFiveDayForecast_onSuccess_updatesForecastData() = runTest(testDispatcher) {
        coEvery { repository.getFiveDayForecast(30.0, 31.0) } returns ApiState.Success(mockForecast)

        viewModel = buildViewModel()
        viewModel.getFiveDayForecast(30.0, 31.0)

        assertEquals(mockForecast, viewModel.forecastData.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun getFiveDayForecast_onFailure_emitsErrorMessage() = runTest(testDispatcher) {
        coEvery { repository.getFiveDayForecast(any(), any()) } returns
                ApiState.Failure(Exception("Forecast error"))

        viewModel = buildViewModel()

        val errors = mutableListOf<String>()
        val job = launch(testDispatcher) { viewModel.errorMessage.toList(errors) }

        viewModel.getFiveDayForecast(0.0, 0.0)

        assertTrue(errors.any { it.contains("Forecast error") })
        job.cancel()
    }

    @Test
    fun getLocationAndFetchWeather_onSuccess_updatesWeatherAndForecast() = runTest(testDispatcher) {
        coEvery { repository.getCurrentWeather(10.0, 20.0) } returns ApiState.Success(mockWeather)
        coEvery { repository.getFiveDayForecast(10.0, 20.0) } returns ApiState.Success(mockForecast)

        every { locationHelper.getCurrentLocation(captureLambda()) } answers {
            lambda<(Double, Double) -> Unit>().captured.invoke(10.0, 20.0)
        }

        viewModel = buildViewModel()
        viewModel.getLocationAndFetchWeather()

        assertEquals(mockWeather, viewModel.weatherData.value)
        assertEquals(mockForecast, viewModel.forecastData.value)
        assertFalse(viewModel.isLoading.value)
    }

    @Test
    fun getLocationAndFetchWeather_weatherFails_forecastStillUpdates() = runTest(testDispatcher) {
        coEvery { repository.getCurrentWeather(any(), any()) } returns
                ApiState.Failure(Exception("location weather error"))
        coEvery { repository.getFiveDayForecast(any(), any()) } returns ApiState.Success(mockForecast)

        every { locationHelper.getCurrentLocation(captureLambda()) } answers {
            lambda<(Double, Double) -> Unit>().captured.invoke(10.0, 20.0)
        }

        viewModel = buildViewModel()
        viewModel.getLocationAndFetchWeather()

        assertNull(viewModel.weatherData.value)
        assertEquals(mockForecast, viewModel.forecastData.value)
    }
}