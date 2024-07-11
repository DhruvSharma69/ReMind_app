package com.example.remind.notification

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.example.remind.repository.Reminder
import java.time.LocalDateTime
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
fun scheduleReminder(context: Context, reminder: Reminder) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (alarmManager.canScheduleExactAlarms()) {
            setExactAlarm(context, alarmManager, reminder)
        } else {
            requestExactAlarmPermission(context)
        }
    } else {
        setExactAlarm(context, alarmManager, reminder)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun setExactAlarm(context: Context, alarmManager: AlarmManager, reminder: Reminder) {
    val intent = Intent(context, ReminderBroadcastReceiver::class.java).apply {
        putExtra("reminderTitle", reminder.reminderTitle)
        putExtra("reminderDescription", reminder.reminderDescription)
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        reminder.hashCode(),
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val reminderDateTime = LocalDateTime.of(reminder.reminderDate, reminder.reminderTime)
    val triggerTime = reminderDateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
}

@RequiresApi(Build.VERSION_CODES.S)
private fun requestExactAlarmPermission(context: Context) {
    val intent = Intent().apply {
        action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
    }
    if (context is Activity) {
        context.startActivityForResult(intent, REQUEST_CODE_SCHEDULE_EXACT_ALARM)
    } else {
        context.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }
}

const val REQUEST_CODE_SCHEDULE_EXACT_ALARM = 1001
