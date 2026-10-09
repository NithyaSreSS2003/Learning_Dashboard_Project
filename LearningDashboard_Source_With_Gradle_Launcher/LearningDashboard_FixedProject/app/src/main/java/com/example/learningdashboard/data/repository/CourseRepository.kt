package com.example.learningdashboard.data.repository

import com.example.learningdashboard.data.local.CourseDao
import com.example.learningdashboard.data.local.CourseEntity
import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.remote.MockCourseRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CourseRepository(
    private val dao: CourseDao,
    private val remote: MockCourseRemoteDataSource
) {
    fun observeCourses(): Flow<List<Course>> =
        dao.observeCourses().map { entities -> entities.map { it.toDomain() } }

    suspend fun refreshCourses() {
        val remoteCourses = remote.fetchCourses()
        // Preserve local lesson completion when refreshing course metadata.
        val current = dao.observeCourses()
        // Remote is mock/static in this assignment; lesson updates are persisted separately.
        dao.upsertAll(remoteCourses.map(CourseEntity::fromDomain))
    }

    suspend fun getCourse(courseId: Int): Course? = dao.getCourse(courseId)?.toDomain()

    suspend fun setLessonCompleted(courseId: Int, lessonId: Int, completed: Boolean) {
        val current = dao.getCourse(courseId)?.toDomain() ?: return
        val updated = current.copy(
            lessons = current.lessons.map {
                if (it.id == lessonId) it.copy(completed = completed) else it
            }
        )
        dao.upsertAll(listOf(CourseEntity.fromDomain(updated)))
    }
}
