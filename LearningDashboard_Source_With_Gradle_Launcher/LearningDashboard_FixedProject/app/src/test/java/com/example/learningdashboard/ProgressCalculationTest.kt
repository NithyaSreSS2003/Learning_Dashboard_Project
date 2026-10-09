package com.example.learningdashboard

import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.model.Lesson
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressCalculationTest {
    @Test
    fun progressIsCalculatedFromCompletedLessons() {
        val course = Course(
            id = 1,
            title = "Kotlin",
            instructor = "Instructor",
            lessons = listOf(
                Lesson(1, "Intro", completed = true),
                Lesson(2, "Types", completed = true),
                Lesson(3, "Functions", completed = false),
                Lesson(4, "Classes", completed = false)
            )
        )
        assertEquals(50, course.progress)
    }

    @Test
    fun emptyCourseHasZeroProgress() {
        val course = Course(2, "Empty", "Instructor", emptyList())
        assertEquals(0, course.progress)
    }
}
