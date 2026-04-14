package com.example.naseem.worker

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object AlertScheduler {
    fun scheduleAlert(
        context: Context,
        triggerTime: Long,
        message: String,
        alertId: Long,
        alertType: AlertType = AlertType.NOTIFICATION
    ) {
        val delay = triggerTime - System.currentTimeMillis()
        if (delay <= 0) {
            return
        }
        val inputData = workDataOf(
            WeatherAlertWorker.KEY_MESSAGE    to message,
            WeatherAlertWorker.KEY_ALERT_ID   to alertId,
            WeatherAlertWorker.KEY_ALERT_TYPE to alertType.name
        )
        val request = OneTimeWorkRequestBuilder<WeatherAlertWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .addTag("alert_$alertId")
            .build()
        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                "alert_$alertId",
                ExistingWorkPolicy.REPLACE,
                request
            )
    }
    fun cancelAlert(context: Context, alertId: Long) {
        WorkManager.getInstance(context).cancelUniqueWork("alert_$alertId")
    }
}