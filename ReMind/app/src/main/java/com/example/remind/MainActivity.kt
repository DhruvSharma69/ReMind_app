package com.example.remind

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import com.example.remind.repository.ReminderList
import com.example.remind.ui.theme.ReMindTheme
import com.example.ui.SetReminderScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReMindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RemindApp()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RemindApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController,
        graph = navController.createGraph(startDestination = "DisplayReminders"){
            composable(route = "DisplayReminders"){
                DisplayRemindersScreen(navController = navController)
            }
            composable(route = "SetReminder"){
                SetReminderScreen(navController = navController)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayRemindersScreen(modifier: Modifier = Modifier, navController: NavController) {

    val reminders by ReminderList.reminders.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = modifier.fillMaxWidth(),
                title = { Text(text = "ReMind", fontSize = 40.sp, fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0f, .7f, .9f),
                    titleContentColor = Color.Yellow)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(route = "SetReminder") }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add reminder")
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(reminders) { reminder ->
                        ReminderCard(reminder = reminder, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReMindTheme {
        RemindApp(Modifier)
    }
}
