package com.example.naseem.data.datasource.remote

import com.example.naseem.common.ApiState
import com.example.naseem.data.dto.ForecastResponse
import com.example.naseem.data.dto.WeatherResponse
import com.example.naseem.data.network.RetrofitHelper
import java.io.IOException

class WeatherRemoteDataSource {
    private val weatherService: WeatherService = RetrofitHelper.weatherService

    suspend fun getCurrentWeather(lat: Double, lon: Double, apiKey: String): ApiState<WeatherResponse> {
        return try {
            val response = weatherService.getCurrentWeatherByLocation(lat, lon, apiKey)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiState.Success(body)
                } else {
                    ApiState.Failure(Exception("Response body is null"))
                }
            } else {
                ApiState.Failure(Exception("Error Code: ${response.code()} - ${response.message()}"))
            }
        } catch (e: IOException) {
            // Handles internet connection issues
            ApiState.Failure(Exception("Check your internet connection", e))
        } catch (e: Exception) {
            // Handles parsing issues or unexpected errors
            ApiState.Failure(e)
        }
    }
    suspend fun getFiveDayForecast(lat: Double, lon: Double, apiKey: String): ApiState<ForecastResponse> {
        return try {
            val response = weatherService.getFiveDayForecast(lat, lon, apiKey)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    ApiState.Success(body)
                } else {
                    ApiState.Failure(Exception("Response body is null"))
                }
            } else {
                ApiState.Failure(Exception("Error Code: ${response.code()} - ${response.message()}"))
            }
        } catch (e: IOException) {
            // Handles internet connection issues
            ApiState.Failure(Exception("Check your internet connection", e))
        } catch (e: Exception) {
            // Handles parsing issues or unexpected errors
            ApiState.Failure(e)
        }
    }
}