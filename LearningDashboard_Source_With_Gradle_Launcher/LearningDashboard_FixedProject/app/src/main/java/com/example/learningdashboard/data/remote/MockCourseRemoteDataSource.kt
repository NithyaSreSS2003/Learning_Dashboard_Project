package com.example.learningdashboard.data.remote

import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.model.Lesson
import kotlinx.coroutines.delay

/**
 * Deterministic stand-in for Retrofit. Throw an exception here to demo API failure.
 * Replace with a Retrofit service in a real app.
 */
class MockCourseRemoteDataSource {
    suspend fun fetchCourses(): List<Course> {
        delay(500)
        return listOf(
            Course(
                id = 1,
                title = "Python Programming",
                instructor = "John Smith",
                lessons = listOf(
                    Lesson(1, "Introduction", true),
                    Lesson(2, "Variables & Data Types", true),
                    Lesson(3, "Functions"),
                    Lesson(4, "OOP")
                )
            ),
            Course(
                id = 2,
                title = "Generative AI",
                instructor = "Sarah Williams",
                lessons = listOf(
                    Lesson(1, "AI Foundations", true),
                    Lesson(2, "Prompt Engineering"),
                    Lesson(3, "Working with Models"),
                    Lesson(4, "Responsible AI"),
                    Lesson(5, "Final Project")
                )
            ),
            Course(
                id = 3,
                title = "Full Stack Development",
                instructor = "David Brown",
                lessons = listOf(
                    Lesson(1, "Web Basics"),
                    Lesson(2, "HTML & CSS"),
                    Lesson(3, "JavaScript"),
                    Lesson(4, "APIs")
                )
            )
        )
    }
}
