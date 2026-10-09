package com.example.learningdashboard.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.data.model.Lesson
import org.json.JSONArray
import org.json.JSONObject

@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val instructor: String,
    val lessonsJson: String
) {
    fun toDomain(): Course {
        val array = JSONArray(lessonsJson)
        val lessons = (0 until array.length()).map { index ->
            val item = array.getJSONObject(index)
            Lesson(
                id = item.getInt("id"),
                title = item.getString("title"),
                completed = item.getBoolean("completed")
            )
        }
        return Course(id, title, instructor, lessons)
    }

    companion object {
        fun fromDomain(course: Course): CourseEntity {
            val array = JSONArray()
            course.lessons.forEach { lesson ->
                array.put(JSONObject().apply {
                    put("id", lesson.id)
                    put("title", lesson.title)
                    put("completed", lesson.completed)
                })
            }
            return CourseEntity(course.id, course.title, course.instructor, array.toString())
        }
    }
}
