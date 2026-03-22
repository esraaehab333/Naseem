package com.example.naseem

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.naseem.data.datasource.local.fav.FavWeatherDao
import com.example.naseem.data.database.FavoriteDatabase
import com.example.naseem.data.models.entity.FavoriteEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.not
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavWeatherDaoTest {

    private lateinit var db: FavoriteDatabase
    private lateinit var dao: FavWeatherDao

    private val entity1 = FavoriteEntity("Cairo", "Cairo, Egypt", 30.0, 31.0)
    private val entity2 = FavoriteEntity("London", "London, UK", 51.5, -0.1)

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            FavoriteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.favWeatherDao()
    }

    @After
    fun teardown() = db.close()

    @Test
    fun insertFavWeather_and_getAllFavWeathers_returnsInsertedItem() = runTest {
        dao.insertFavWeather(entity1)
        val result = dao.getAllFavWeathers().first()
        assertThat(result, hasItem(entity1))
    }

    @Test
    fun insertDuplicate_replacesExisting() = runTest {
        dao.insertFavWeather(entity1)
        val updated = entity1.copy(cityName = "Cairo Updated")
        dao.insertFavWeather(updated)
        val result = dao.getAllFavWeathers().first()
        assert(result.size == 1)
        assert(result[0].cityName == "Cairo Updated")
    }

    @Test
    fun deleteFavWeather_removesItem() = runTest {
        dao.insertFavWeather(entity1)
        dao.insertFavWeather(entity2)
        dao.deleteFavWeather(entity1)
        val result = dao.getAllFavWeathers().first()
        assertThat(result, not(hasItem(entity1)))
        assertThat(result, hasItem(entity2))
    }

    @Test
    fun getAllFavWeathers_emptyDb_returnsEmptyList() = runTest {
        val result = dao.getAllFavWeathers().first()
        assert(result.isEmpty())
    }
}