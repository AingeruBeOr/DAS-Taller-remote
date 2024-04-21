package com.example.proyecto1.ui.widgets.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

class TallerAlarmScheduler(
    private val context: Context
) : AlarmScheduler {

    // Get the AlarmManager (service from Android OS)
    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    // Get the intent to call the BroadcastReceiver which executes the task
    private val intent = Intent(context, AlarmReceiver::class.java)

    override fun schedule() {
        Log.d("Widget", "Widget alarm scheduling")
        // Set the repeating alarm
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 60000, // Cuando se ejecuta por primera vez
            60000, // Se ejecuta cada cada 15000ms = 15s
            PendingIntent.getBroadcast(
                context,
                0, // Unique ID for the pending intent
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel() {
        Log.d("Widget", "Widget alarm canceling")
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                0, // Unique ID for the pending intent
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}