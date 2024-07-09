
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remind.repository.Reminder


class ReminderViewmodel : ViewModel() {
    // Private mutable LiveData
    private val _reminders = MutableLiveData<List<Reminder>>(emptyList())  // Changed to use MutableLiveData

    // Public immutable LiveData
    val reminders: LiveData<List<Reminder>> get() = _reminders  // Changed to use _reminders

    // Function to add a reminder
    fun addReminder(reminder: Reminder) {
        _reminders.value = _reminders.value?.plus(reminder)  // Changed to use _reminders
    }

    // Function to remove a reminder
    fun deleteReminder(reminder: Reminder) {
        _reminders.value = _reminders.value?.minus(reminder)  // Changed to use _reminders
    }
}