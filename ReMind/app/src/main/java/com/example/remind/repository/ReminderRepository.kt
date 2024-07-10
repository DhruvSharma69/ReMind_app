package com.example.remind.repository

import android.os.Build
import androidx.annotation.RequiresApi

class ReminderRepository {

    // A repository holds the function to communicate with databases

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchReminders(): List<Reminder> {
        TODO()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addReminder(reminder: Reminder){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteReminder(reminder: Reminder){

    }


}
