package com.example.remind.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.remind.repository.Reminder

@Database(entities = [Reminder::class], version = 1)
@TypeConverters(Converters::class)
abstract class ReminderDatabase: RoomDatabase() {
    companion object { const val name = "ReminderDb" }

    abstract fun getDao(): ReminderDao
}