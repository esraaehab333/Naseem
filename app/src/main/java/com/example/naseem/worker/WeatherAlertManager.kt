package com.example.naseem.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
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
        const val CHANNEL_ID_ALARM        = "weather_alarm_channel"
        const val CHANNEL_ID_NOTIFICATION = "weather_notification_channel"

        const val KEY_MESSAGE    = "message"
        const val KEY_ALERT_ID   = "alert_id"
        const val KEY_ALERT_TYPE = "alert_type"   // pass AlertType.name as String
    }

    override fun doWork(): Result {
        val message   = inputData.getString(KEY_MESSAGE)    ?: "Weather Alert"
        val alertId   = inputData.getLong(KEY_ALERT_ID, System.currentTimeMillis())
        val alertType = inputData.getString(KEY_ALERT_TYPE)
            ?.let { runCatching { AlertType.valueOf(it) }.getOrNull() }
            ?: AlertType.NOTIFICATION          // safe default

        applicationContext
            .getSharedPreferences("triggered_alerts", Context.MODE_PRIVATE)
            .edit()
            .putBoolean("triggered_$alertId", true)
            .apply()

        when (alertType) {
            AlertType.ALARM -> showAlarmNotification(message, alertId)
            AlertType.NOTIFICATION -> showSimpleNotification(message, alertId)
        }

        return Result.success()
    }

    private fun showAlarmNotification(message: String, alertId: Long) {
        val notificationId = alertId.toInt()
        val manager = getManager()

        val alarmSoundUri = rawUri(R.raw.sound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID_ALARM,
                "Weather Alarms",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(
                    alarmSoundUri,
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500, 200, 500)
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID_ALARM)
            .setSmallIcon(R.drawable.ic_alert_type)
            .setContentTitle("🚨 Naseem Weather Alarm")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSound(alarmSoundUri)
            .setVibrate(longArrayOf(0, 500, 200, 500, 200, 500))
            .setAutoCancel(false)
            .setOngoing(true)
            .addAction(R.drawable.ic_alert_type, "Dismiss", dismissIntent(notificationId))
            .addAction(R.drawable.ic_alert_type, "Snooze 5 min", snoozeIntent(notificationId, message, alertId))
            .build()

        manager.notify(notificationId, notification)
    }

    private fun showSimpleNotification(message: String, alertId: Long) {
        val notificationId = alertId.toInt()
        val manager = getManager()
        val notifSoundUri = rawUri(R.raw.sound)

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

    private fun dismissIntent(notificationId: Int): PendingIntent {
        val intent = Intent(applicationContext, AlarmDismissReceiver::class.java)
            .putExtra("notification_id", notificationId)
        return PendingIntent.getBroadcast(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun snoozeIntent(notificationId: Int, message: String, alertId: Long): PendingIntent {
        val intent = Intent(applicationContext, AlarmSnoozeReceiver::class.java)
            .putExtra("notification_id", notificationId)
            .putExtra("message", message)
            .putExtra("alert_id", alertId)
        return PendingIntent.getBroadcast(
            applicationContext,
            notificationId + 1000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}