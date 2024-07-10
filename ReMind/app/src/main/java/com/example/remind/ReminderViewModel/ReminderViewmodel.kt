
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.remind.repository.Reminder
import com.example.remind.repository.ReminderRepository
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
class ReminderViewmodel(private val reminderRepository:ReminderRepository) : ViewModel() {
   //viewmodel will handle state changes and will talk to ui and repository(data)
    // this variable will mark internal changes to data through its class methods
    private val _reminders = MutableLiveData<List<Reminder>>()

    val reminders:LiveData<List<Reminder>> get() = _reminders

    // A constructor here is mandatory to reflect the state of database into _reminders
    init {
        loadReminders()
    }

     @RequiresApi(Build.VERSION_CODES.O)
     private fun loadReminders(){
         viewModelScope.launch {
             val remindersFromDatabase = reminderRepository.fetchReminders().toMutableList()
             _reminders.value = remindersFromDatabase
         }
     }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.addReminder(reminder)
            loadReminders()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteReminder(reminder: Reminder){
        viewModelScope.launch {
            reminderRepository.deleteReminder(reminder)
            loadReminders()
        }
    }

}