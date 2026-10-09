package com.example.learningdashboard.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.learningdashboard.data.model.Course
import com.example.learningdashboard.presentation.dashboard.DashboardUiState
import com.example.learningdashboard.presentation.login.LoginUiState

@Composable
fun LoginScreen(
    state: LoginUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLogin: () -> Unit,
    onLoggedIn: () -> Unit
) {
    if (state.loggedIn) {
        androidx.compose.runtime.LaunchedEffect(Unit) { onLoggedIn() }
    }
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text("Welcome back", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Sign in to continue learning.", modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
            OutlinedTextField(
                value = state.email, onValueChange = onEmailChanged,
                label = { Text("Email") }, singleLine = true, modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(
                value = state.password, onValueChange = onPasswordChanged,
                label = { Text("Password") }, singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            state.error?.let { Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp)) }
            Button(
                onClick = onLogin, enabled = !state.loading,
                modifier = Modifier.fillMaxWidth().padding(top = 20.dp)
            ) {
                if (state.loading) CircularProgressIndicator() else Text("Login")
            }
            Text("Demo: any valid email + password (4+ characters)", style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 12.dp))
        }
    }
}

@Composable
fun DashboardScreen(
    state: DashboardUiState,
    onRefresh: () -> Unit,
    onCourseClick: (Course) -> Unit
) {
    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.weight(1f)) {
                    Text("My Learning", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("Keep building your skills")
                }
                TextButton(onClick = onRefresh) { Text("Refresh") }
            }
            Spacer(Modifier.height(16.dp))
            if (state.loading && state.courses.isEmpty()) {
                Column(Modifier.fillMaxWidth().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Text("Loading courses…", modifier = Modifier.padding(top = 12.dp))
                }
            } else if (state.courses.isEmpty()) {
                Text(state.error ?: "No courses available.")
                Button(onClick = onRefresh, modifier = Modifier.padding(top = 12.dp)) { Text("Try again") }
            } else {
                state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(state.courses, key = { it.id }) { course ->
                        Card(Modifier.fillMaxWidth().clickable { onCourseClick(course) }) {
                            Column(Modifier.padding(16.dp)) {
                                Text(course.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                                Text("Instructor: ${course.instructor}", modifier = Modifier.padding(top = 4.dp))
                                Text("${course.progress}% complete • ${course.lessonCount} lessons",
                                    modifier = Modifier.padding(top = 8.dp))
                                LinearProgressIndicator(
                                    progress = { course.progress / 100f },
                                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                                )
                                Button(onClick = { onCourseClick(course) }, modifier = Modifier.padding(top = 8.dp)) {
                                    Text("Continue")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseDetailsScreen(
    course: Course?,
    onToggleLesson: (Int, Boolean) -> Unit,
    onBack: () -> Unit
) {
    Scaffold { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            TextButton(onClick = onBack) { Text("← Back to courses") }
            if (course == null) {
                CircularProgressIndicator()
            } else {
                Text(course.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text("Instructor: ${course.instructor}", modifier = Modifier.padding(top = 4.dp))
                Text("${course.progress}% complete", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 12.dp))
                LinearProgressIndicator(
                    progress = { course.progress / 100f },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 20.dp)
                )
                Text("Lessons", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                LazyColumn {
                    items(course.lessons, key = { it.id }) { lesson ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = lesson.completed,
                                onCheckedChange = { onToggleLesson(lesson.id, it) }
                            )
                            Column(Modifier.weight(1f)) {
                                Text(lesson.title)
                                Text(if (lesson.completed) "Completed" else "Pending",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = if (lesson.completed) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }
            }
        }
    }
}
