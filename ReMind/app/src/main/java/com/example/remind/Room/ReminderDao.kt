package com.example.remind.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.remind.repository.Reminder

@Dao
interface ReminderDao {
    @Upsert
    suspend fun addReminder(reminder:Reminder)
    @Delete
    suspend fun deleteReminder(reminder:Reminder)

    @Query("SELECT * FROM REMINDER ORDER BY REMINDERDATE ASC")
    suspend fun getReminders(): List<Reminder>
}