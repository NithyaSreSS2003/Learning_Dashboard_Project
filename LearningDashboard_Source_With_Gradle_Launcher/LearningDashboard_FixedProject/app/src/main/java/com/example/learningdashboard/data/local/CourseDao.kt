package com.example.learningdashboard.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY id")
    fun observeCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(courses: List<CourseEntity>)

    @Query("SELECT * FROM courses WHERE id = :courseId LIMIT 1")
    suspend fun getCourse(courseId: Int): CourseEntity?

    @Query("DELETE FROM courses")
    suspend fun clear()
}
