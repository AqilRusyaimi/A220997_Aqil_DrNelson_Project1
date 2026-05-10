package com.example.a220997_aqil_drnelson_project1

import android.R
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class ReportUiState(
    val subject: String = "",
    val category: String = "",
    val description: String = "",
    val location: String = "",
    val date: String = ""
)
data class UserUiState(
    val username: String = "",
    val reportCount: Int = 3
)

class UserViewModel : ViewModel(){
    private val _uiState = MutableStateFlow(UserUiState(username = "Guest"))

    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    fun setUsername(name: String){
        _uiState.update { currentState ->
            currentState.copy(
                username = name
            )
        }
    }

    fun addCount(){
        _uiState.update { currentState ->
            currentState.copy(
                reportCount = currentState.reportCount+1
            )
        }
    }
}
class ReportViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReportUiState(date = setDate()))

    val uiState: StateFlow<ReportUiState> = _uiState.asStateFlow()

    fun setData(subject: String, category: String, description: String,
                location: String) {
        _uiState.update { currentState ->
            currentState.copy(
                subject = subject,
                category = category,
                description = description,
                location = location
            )
        }
    }

    fun resetReport() {
        _uiState.value = ReportUiState(date = setDate())
    }

    private fun setDate(): String {
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()

        return formatter.format(calendar.time)
    }
}