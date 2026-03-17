package com.example.naseem

import com.example.naseem.common.ApiState
import com.example.naseem.data.datasource.WeatherRepository
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.entity.FavoriteEntity
import com.example.naseem.data.model.FavoriteModel
import com.example.naseem.presentation.fav.viewModels.FavoriteViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteViewModelTest {

    private lateinit var repository: WeatherRepository
    private lateinit var viewModel: FavoriteViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    private val entity = FavoriteEntity("Cairo", "Cairo, Egypt", 30.0, 31.0)
    private val model = FavoriteModel(entity)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk(relaxed = true)
        every { repository.getAllFavWeather() } returns flowOf(listOf(entity))
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }

    // بيبني ViewModel ويحقن الـ testDispatcher بدل Dispatchers.IO
    private fun buildViewModel(): FavoriteViewModel {
        return FavoriteViewModel(repository, testDispatcher)
    }

    @Test
    fun getAllFavorites_emitsSuccessState() = runTest(testDispatcher) {
        every { repository.getAllFavWeather() } returns flowOf(listOf(entity))

        viewModel = buildViewModel()

        val state = viewModel.favoritesState.value
        assertTrue(state is ApiState.Success)
        assertEquals(1, (state as ApiState.Success).data.size)
    }

    @Test
    fun getAllFavorites_onError_emitsFailureState() = runTest(testDispatcher) {
        every { repository.getAllFavWeather() } returns flow { throw Exception("DB error") }

        viewModel = buildViewModel()

        val state = viewModel.favoritesState.value
        assertTrue(state is ApiState.Failure)
    }

    @Test
    fun addToFavorites_emitsAddEvent() = runTest(testDispatcher) {
        coEvery { repository.insertWeatherToFav(any()) } just Runs
        viewModel = buildViewModel()

        val events = mutableListOf<String>()
        val job = launch(testDispatcher) { viewModel.addEvent.toList(events) }

        viewModel.addToFavorites(model)

        assertTrue(events.any { it.contains("added to favorites") })
        job.cancel()
    }

    @Test
    fun addToFavorites_onError_emitsSomethingWentWrong() = runTest(testDispatcher) {
        coEvery { repository.insertWeatherToFav(any()) } throws Exception("insert failed")
        viewModel = buildViewModel()

        val events = mutableListOf<String>()
        val job = launch(testDispatcher) { viewModel.addEvent.toList(events) }

        viewModel.addToFavorites(model)

        assertTrue(events.any { it == "Something went wrong" })
        job.cancel()
    }

    @Test
    fun deleteFromFavorites_emitsDeleteEvent() = runTest(testDispatcher) {
        coEvery { repository.deleteWeatherFromFav(any()) } just Runs
        viewModel = buildViewModel()

        val events = mutableListOf<String>()
        val job = launch(testDispatcher) { viewModel.deleteEvent.toList(events) }

        viewModel.deleteFromFavorites(model)

        assertTrue(events.any { it.contains("deleted from favorites") })
        job.cancel()
    }

    @Test
    fun getWeatherForFavoriteLocation_updatesSelectedWeather() = runTest(testDispatcher) {
        val mockWeather = mockk<WeatherResponse>()
        coEvery { repository.getCurrentWeather(any(), any()) } returns ApiState.Success(mockWeather)
        coEvery { repository.getFiveDayForecast(any(), any()) } returns ApiState.Failure(Exception())

        viewModel = buildViewModel()
        viewModel.getWeatherForFavoriteLocation(30.0, 31.0)

        assertEquals(mockWeather, viewModel.selectedWeather.value)
    }
}