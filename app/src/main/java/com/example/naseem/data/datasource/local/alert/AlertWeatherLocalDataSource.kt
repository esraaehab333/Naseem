package com.example.naseem.data.datasource.local.alert

import android.content.Context
import com.example.naseem.data.db.AlertDataBase
import com.example.naseem.data.entity.AlertEntity
import kotlinx.coroutines.flow.Flow

class AlertWeatherLocalDataSource(context: Context) {
    private val alertWeatherDao: AlertWeatherDao = AlertDataBase.getInstance(context =context).alertWeatherDao()

    suspend fun insertAlertWeather(alert: AlertEntity) {
        alertWeatherDao.insertAlertWeather(alert)
    }

    suspend fun deleteAlertWeather(alert: AlertEntity) {
        alertWeatherDao.deleteAlertWeather(alert)
    }

    fun getAllAlertWeather(): Flow<List<AlertEntity>> {
        return alertWeatherDao.getAllAlertWeathers()
    }
}