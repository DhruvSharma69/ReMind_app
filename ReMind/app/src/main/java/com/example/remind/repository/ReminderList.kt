package com.example.remind.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.time.LocalDate
import java.time.LocalTime

object ReminderList {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _reminders: MutableLiveData<List<Reminder>> = MutableLiveData(listOf(
        Reminder("sample description", "sample title", true, LocalDate.now(), LocalTime.now())
    ))
    val reminders: LiveData<List<Reminder>> @RequiresApi(Build.VERSION_CODES.O)
    get() = _reminders

    @RequiresApi(Build.VERSION_CODES.O)
    fun addReminder(reminder: Reminder){
        val currentList = _reminders.value.orEmpty().toMutableList()
        currentList.add(reminder)
        _reminders.value = currentList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun clearAllReminder(){

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteReminder(reminder: Reminder){
        val currentList = _reminders.value.orEmpty().toMutableList()
        currentList.remove(reminder)
        _reminders.value = currentList
    }
//using
    @RequiresApi(Build.VERSION_CODES.O)
    fun getReminders(): List<Reminder> {
        return reminders.value?.toList() ?: listOf<Reminder>()
    }
}
