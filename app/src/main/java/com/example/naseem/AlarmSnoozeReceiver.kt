package com.example.naseem

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.work.*
import com.example.naseem.presentation.alert.components.WeatherAlertWorker
import java.util.concurrent.TimeUnit

class AlarmSnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notification_id", -1)
        val message = intent.getStringExtra("message") ?: "Weather Alert"
        val alertId = intent.getLongExtra("alert_id", System.currentTimeMillis())

        if (notificationId != -1) {
            NotificationManagerCompat.from(context).cancel(notificationId)
        }

        val data = workDataOf(
            "message" to message,
            "alert_id" to alertId
        )
        val snoozeRequest = OneTimeWorkRequestBuilder<WeatherAlertWorker>()
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(snoozeRequest)
    }
}