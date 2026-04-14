package com.example.naseem.worker
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.naseem.R
private const val TAG = "AlarmForegroundService"

class AlarmForegroundService : Service() {
    private var mediaPlayer: MediaPlayer? = null
    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val ACTION_SNOOZE = "ACTION_SNOOZE"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_ALERT_ID = "alert_id"
        const val CHANNEL_ID = "alarm_foreground_channel_v3"
        const val NOTIFICATION_ID = 9999
    }
    override fun onBind(intent: Intent?): IBinder? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val message = intent.getStringExtra(EXTRA_MESSAGE) ?: "Weather Alarm"
                val alertId = intent.getLongExtra(EXTRA_ALERT_ID, 0L)
                startAlarm(message, alertId)
            }
            ACTION_DISMISS -> {
                stopAlarm()
            }
            ACTION_SNOOZE -> {
                val message = intent.getStringExtra(EXTRA_MESSAGE) ?: "Weather Alarm"
                val alertId = intent.getLongExtra(EXTRA_ALERT_ID, 0L)
                snoozeAlarm(message, alertId)
            }
        }
        return START_NOT_STICKY
    }
    @SuppressLint("ForegroundServiceType")
    private fun startAlarm(message: String, alertId: Long) {
        createChannel()
        val dismissIntent = Intent(this, AlarmForegroundService::class.java).apply {
            action = ACTION_DISMISS
        }
        val dismissPending = PendingIntent.getService(
            this, 0, dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeIntent = Intent(this, AlarmForegroundService::class.java).apply {
            action = ACTION_SNOOZE
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_ALERT_ID, alertId)
        }
        val snoozePending = PendingIntent.getService(
            this, 1, snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = buildNotification(message, dismissPending, snoozePending)
        startForeground(NOTIFICATION_ID, notification)
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                val afd = resources.openRawResourceFd(R.raw.sound)
                setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
                afd.close()
                isLooping = true
                prepare()
                start()
            }
    }

    private fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    private fun snoozeAlarm(message: String, alertId: Long) {
        stopAlarm()
        AlertScheduler.scheduleAlert(
            context = this,
            triggerTime = System.currentTimeMillis() + 5 * 60 * 1000,
            message = message,
            alertId = alertId,
            alertType = AlertType.ALARM
        )
    }
    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Active Alarms",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                setSound(null, null)
                enableVibration(true)
                vibrationPattern = longArrayOf(0, 500, 200, 500)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(
        message: String,
        dismissPending: PendingIntent,
        snoozePending: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_alert_type)
            .setContentTitle("🔔 Naseem Weather Alarm")
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)
            .setAutoCancel(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(R.drawable.ic_alert_type, "Dismiss", dismissPending)
            .addAction(R.drawable.ic_alert_type, "Snooze 5 min", snoozePending)
            .build()
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        mediaPlayer = null
        super.onDestroy()
    }
}