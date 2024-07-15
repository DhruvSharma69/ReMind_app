package com.example.remind.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.remind.Room.ReminderDao

class ReminderRepository(private val reminderDao: ReminderDao) {

    // A repository holds the function to communicate with databases

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchReminders(): List<Reminder> {

        return reminderDao.getReminders()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addReminder(reminder: Reminder){
        reminderDao.addReminder(reminder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteReminder(reminder: Reminder){
        reminderDao.deleteReminder(reminder)
    }


}
