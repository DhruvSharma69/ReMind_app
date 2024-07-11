import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.remind.repository.ReminderRepository

class ReminderViewModelFactory(private val repository: ReminderRepository,
    private val context: Context) : ViewModelProvider.Factory {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReminderViewmodel::class.java)) {
            return ReminderViewmodel(repository, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
