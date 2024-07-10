package com.example.remind.repository

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity
data class Reminder(
    val reminderDescription:String = "",
    val reminderTitle:String ,
    val reminderStatus:Boolean = true, //if true reminder is active
    val reminderDate:LocalDate,
    val reminderTime: LocalTime,
    @PrimaryKey(autoGenerate = true)
    val id :Int = 0
)
