package com.example.naseem.worker
import android.media.MediaPlayer

object AlarmSoundManager {
    private var mediaPlayer: MediaPlayer? = null
    fun stop() {
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
    }
}