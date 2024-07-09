package com.example.remind.repository

import java.sql.Time
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Reminder(
    val reminderDescription:String = "",
    val reminderTitle:String ,
    val reminderStatus:Boolean = true, //if true reminder is active
    val reminderDate:LocalDate,
    val reminderTime: LocalTime
)
