package com.example.naseem.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.naseem.R

enum class AlertType {
    ALARM,
    NOTIFICATION
}

class WeatherAlertWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    companion object {
        const val CHANNEL_ID_NOTIFICATION = "weather_notification_channel"
        const val KEY_MESSAGE = "message"
        const val KEY_ALERT_ID = "alert_id"
        const val KEY_ALERT_TYPE = "alert_type"
    }

    override fun doWork(): Result {
        val message        = inputData.getString(KEY_MESSAGE) ?: "Weather Alert"
        val alertId        = inputData.getLong(KEY_ALERT_ID, System.currentTimeMillis())
        val alertTypeRaw   = inputData.getString(KEY_ALERT_TYPE)
        val alertType      = alertTypeRaw
            ?.let { runCatching { AlertType.valueOf(it) }.getOrNull() }
            ?: AlertType.NOTIFICATION
        applicationContext
            .getSharedPreferences("triggered_alerts", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("triggered_$alertId", true)
            .apply()

        when (alertType) {
            AlertType.ALARM        -> {
                showAlarmNotification(message, alertId)
            }
            AlertType.NOTIFICATION -> {
                showSimpleNotification(message, alertId)
            }
        }

        return Result.success()
    }

    private fun showAlarmNotification(message: String, alertId: Long) {
        val intent = Intent(applicationContext, AlarmForegroundService::class.java).apply {
            action = AlarmForegroundService.ACTION_START
            putExtra(AlarmForegroundService.EXTRA_MESSAGE, message)
            putExtra(AlarmForegroundService.EXTRA_ALERT_ID, alertId)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            applicationContext.startForegroundService(intent)
        } else {
            applicationContext.startService(intent)
        }
    }

    private fun showSimpleNotification(message: String, alertId: Long) {
        val notificationId = alertId.toInt()
        val manager        = getManager()
        val notifSoundUri  = rawUri(R.raw.sound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_NOTIFICATION,
                "Weather Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                setSound(
                    notifSoundUri,
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                enableVibration(false)
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_NOTIFICATION)
            .setSmallIcon(R.drawable.ic_alert_type)
            .setContentTitle("🌤 Naseem Weather Alert")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(notifSoundUri)
            .setAutoCancel(true)
            .build()
        manager.notify(notificationId, notification)
    }

    private fun getManager() =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun rawUri(resId: Int): Uri =
        Uri.parse("android.resource://${applicationContext.packageName}/$resId")
}