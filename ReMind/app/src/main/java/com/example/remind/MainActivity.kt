package com.example.remind

import ReminderViewModelFactory
import ReminderViewmodel
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.room.Room
import com.example.remind.Room.ReminderDatabase
import com.example.remind.repository.ReminderRepository
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            "reminderChannel",
                            "Reminder Notifications",
                            NotificationManager.IMPORTANCE_HIGH
                        )
                        val manager = getSystemService(NotificationManager::class.java)
                        manager.createNotificationChannel(channel)
                    }

                    val database = Room.databaseBuilder(context = applicationContext, klass = ReminderDatabase::class.java, name = ReminderDatabase.name).build()
                    val reminderRepository = ReminderRepository(database.getDao())

                    RemindApp(applicationContext, reminderRepository = reminderRepository)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RemindApp(context: Context,modifier: Modifier = Modifier, reminderRepository:ReminderRepository) {


    val viewModelFactory = ReminderViewModelFactory(reminderRepository, context)
    val reminderViewModel: ReminderViewmodel = viewModel(factory = viewModelFactory)
    val navController = rememberNavController()

    NavHost(navController = navController,
        graph = navController.createGraph(startDestination = NavRoutes.DisplayScreen.routes){
            composable(route = NavRoutes.DisplayScreen.routes){
                DisplayRemindersScreen(navController = navController,
                    reminderViewModel = reminderViewModel
                )
            }
            composable(route = NavRoutes.SetReminder.routes){
                SetReminderScreen(navController = navController, reminderViewModel = reminderViewModel)
            }
        }
    )



}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DisplayRemindersScreen(modifier: Modifier = Modifier, navController: NavController, reminderViewModel: ReminderViewmodel) {

    val reminders by reminderViewModel.reminders.observeAsState(emptyList())

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
            FloatingActionButton(onClick = { navController.navigate(route = NavRoutes.SetReminder.routes) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add reminder")
            }
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(reminders) { reminder ->
                        ReminderCard(reminder = reminder, modifier = Modifier.fillMaxWidth(), reminderViewModel = reminderViewModel)
                    }
                }
            }
        }
    )
}
enum class NavRoutes(val routes: String){
    DisplayScreen("displayScreen"),
    SetReminder("setReminder")
}

