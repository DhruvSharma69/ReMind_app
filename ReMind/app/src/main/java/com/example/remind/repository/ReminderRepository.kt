package com.example.remind.repository

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.LocalTime

class ReminderRepository {

    // A repository holds the function to communicate with databases

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchReminders(): List<Reminder> {
        return listOf(Reminder("samepleDes", "sampleTitle", true, LocalDate.of(2024, 7, 11), LocalTime.of(14,29)))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun addReminder(reminder: Reminder){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun deleteReminder(reminder: Reminder){

    }


}
