package com.example.learningdashboard.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    private val courseId: Int,
    private val repository: CourseRepository
) : ViewModel() {
    private val _course = MutableStateFlow<Course?>(null)
    val course: StateFlow<Course?> = _course.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeCourses().collect { courses ->
                _course.value = courses.firstOrNull { it.id == courseId }
            }
        }
    }

    fun toggleLesson(lessonId: Int, completed: Boolean) {
        viewModelScope.launch {
            repository.setLessonCompleted(courseId, lessonId, completed)
        }
    }
}
