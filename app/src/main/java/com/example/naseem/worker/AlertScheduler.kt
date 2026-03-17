package com.example.naseem.worker


import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object AlertScheduler {

    fun scheduleAlert(
        context: Context,
        triggerTime: Long,
        message: String,
        alertId: Long
    ) {
        val delay = triggerTime - System.currentTimeMillis()
        if (delay <= 0) return

        val inputData = workDataOf(
            "message" to message,
            "alert_id" to alertId
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