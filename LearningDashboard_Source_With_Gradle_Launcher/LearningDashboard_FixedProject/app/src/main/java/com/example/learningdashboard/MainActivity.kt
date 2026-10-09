package com.example.learningdashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.room.Room
import com.example.learningdashboard.data.local.CourseDatabase
import com.example.learningdashboard.data.remote.MockCourseRemoteDataSource
import com.example.learningdashboard.data.repository.CourseRepository
import com.example.learningdashboard.navigation.LearningApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = Room.databaseBuilder(
            applicationContext,
            CourseDatabase::class.java,
            "learning-dashboard.db"
        ).build()
        val repository = CourseRepository(
            dao = database.courseDao(),
            remote = MockCourseRemoteDataSource()
        )
        setContent {
            MaterialTheme {
                Surface {
                    LearningApp(repository = repository)
                }
            }
        }
    }
}
