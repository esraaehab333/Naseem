package com.example.naseem.utils

import java.util.concurrent.TimeUnit

fun getRemainingTime(targetDateMillis: Long): String {
    val now = System.currentTimeMillis()
    val diff = targetDateMillis - now
    return when {
        diff <= 0 -> "Expired"
        diff < TimeUnit.HOURS.toMillis(1) -> {
            val mins = TimeUnit.MILLISECONDS.toMinutes(diff)
            "${mins}m remaining"
        }
        diff < TimeUnit.DAYS.toMillis(1) -> {
            val hours = TimeUnit.MILLISECONDS.toHours(diff)
            val mins = TimeUnit.MILLISECONDS.toMinutes(diff) % 60
            "${hours}h ${mins}m remaining"
        }
        else -> {
            val days = TimeUnit.MILLISECONDS.toDays(diff)
            "${days}d remaining"
        }
    }
}