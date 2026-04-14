package com.example.naseem.worker

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.*
import java.util.concurrent.TimeUnit

class AlarmSnoozeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notificationId = intent.getIntExtra("notification_id", -1)
        val message = intent.getStringExtra("message") ?: "Weather Alert"
        val alertId = intent.getLongExtra("alert_id", System.currentTimeMillis())

        if (notificationId != -1) {
            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.cancel(notificationId)
            AlarmSoundManager.stop()
        }
        val data = workDataOf(
            WeatherAlertWorker.KEY_MESSAGE    to message,
            WeatherAlertWorker.KEY_ALERT_ID   to alertId,
            WeatherAlertWorker.KEY_ALERT_TYPE to AlertType.ALARM.name
        )
        val snoozeRequest = OneTimeWorkRequestBuilder<WeatherAlertWorker>()
            .setInitialDelay(5, TimeUnit.MINUTES)
            .setInputData(data)
            .build()
        WorkManager.getInstance(context).enqueue(snoozeRequest)
    }
}