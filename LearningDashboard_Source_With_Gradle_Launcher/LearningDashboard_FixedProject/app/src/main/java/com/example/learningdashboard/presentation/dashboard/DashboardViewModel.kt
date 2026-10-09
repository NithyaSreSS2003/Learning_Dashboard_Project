package com.example.learningdashboard.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class DashboardUiState(
    val courses: List<Course> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class DashboardViewModel(private val repository: CourseRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeCourses()
                .catch { error ->
                    _uiState.value = _uiState.value.copy(loading = false, error = error.message)
                }
                .collect { courses ->
                    _uiState.value = _uiState.value.copy(
                        courses = courses,
                        loading = false,
                        error = if (courses.isEmpty()) _uiState.value.error else null
                    )
                }
        }
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            try {
                repository.refreshCourses()
                _uiState.value = _uiState.value.copy(loading = false, error = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    loading = false,
                    error = if (_uiState.value.courses.isEmpty())
                        "Unable to load courses. Please retry."
                    else null
                )
            }
        }
    }
}
