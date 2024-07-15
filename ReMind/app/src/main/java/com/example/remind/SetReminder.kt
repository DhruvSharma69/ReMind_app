package com.example.ui

import ReminderViewmodel
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.remind.NavRoutes
import com.example.remind.repository.Reminder
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SetReminderScreen(
    modifier: Modifier = Modifier, navController: NavController, reminderViewModel:ReminderViewmodel
) {
    var title by remember { mutableStateOf(TextFieldValue()) }
    var description by remember { mutableStateOf(TextFieldValue()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    val context = LocalContext.current

    val currentDate = LocalDate.now()
    val currentTime = LocalTime.now()

    // Date Picker Dialog
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, monthOfYear, dayOfMonth ->
            selectedDate = LocalDate.of(year, monthOfYear + 1, dayOfMonth)
        },
        selectedDate.year,
        selectedDate.monthValue - 1,
        selectedDate.dayOfMonth
    )
    datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000

    // Time Picker Dialog
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val pickedTime = LocalTime.of(hourOfDay, minute)
            if (selectedDate == currentDate && pickedTime.isBefore(currentTime)) {
                Toast.makeText(context, "Cannot pick a past time", Toast.LENGTH_SHORT).show()
            } else {
                selectedTime = pickedTime
            }
        },

        selectedTime.hour,
        selectedTime.minute,
        true
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Set Reminder", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(onClick = { datePickerDialog.show() }) {
                Text("Date: $selectedDate")
            }
            Spacer(modifier = Modifier.width(16.dp))
            OutlinedButton(onClick = { timePickerDialog.show() }) {
                Text("Time: $selectedTime")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val reminder = Reminder(
                    reminderDescription = description.text,
                    reminderTitle = title.text,
                    reminderStatus = true,
                    reminderDate = selectedDate,
                    reminderTime = selectedTime
                )
                reminderViewModel.addReminder(reminder = reminder)
                navController.navigate(route = NavRoutes.DisplayScreen.routes)
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Save")
        }
    }
}
