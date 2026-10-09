package com.example.learningdashboard.data.model

data class Course(
    val id: Int,
    val title: String,
    val instructor: String,
    val lessons: List<Lesson>
) {
    val lessonCount: Int get() = lessons.size
    val progress: Int
        get() = if (lessons.isEmpty()) 0 else (lessons.count { it.completed } * 100) / lessons.size
}

data class Lesson(
    val id: Int,
    val title: String,
    val completed: Boolean = false
)
