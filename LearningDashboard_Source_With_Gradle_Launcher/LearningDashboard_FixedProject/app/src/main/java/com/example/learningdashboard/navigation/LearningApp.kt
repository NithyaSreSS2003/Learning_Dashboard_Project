package com.example.learningdashboard.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.learningdashboard.data.repository.CourseRepository
import com.example.learningdashboard.presentation.dashboard.DashboardViewModel
import com.example.learningdashboard.presentation.details.CourseDetailsViewModel
import com.example.learningdashboard.presentation.login.LoginViewModel
import com.example.learningdashboard.ui.DashboardScreen
import com.example.learningdashboard.ui.CourseDetailsScreen
import com.example.learningdashboard.ui.LoginScreen

@Composable
fun LearningApp(repository: CourseRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val vm: LoginViewModel = viewModel()
            val state by vm.uiState.collectAsState()
            LoginScreen(state, vm::onEmailChanged, vm::onPasswordChanged, vm::login) {
                navController.navigate("dashboard") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
        composable("dashboard") {
            val vm: DashboardViewModel = viewModel(
                factory = SimpleViewModelFactory { DashboardViewModel(repository) }
            )
            val state by vm.uiState.collectAsState()
            DashboardScreen(state, vm::refresh) { course ->
                navController.navigate("details/${course.id}")
            }
        }
        composable(
            route = "details/{courseId}",
            arguments = listOf(navArgument("courseId") { type = NavType.IntType })
        ) { entry ->
            val courseId = entry.arguments?.getInt("courseId") ?: return@composable
            val vm: CourseDetailsViewModel = viewModel(
                key = "details-$courseId",
                factory = SimpleViewModelFactory { CourseDetailsViewModel(courseId, repository) }
            )
            val course by vm.course.collectAsState()
            CourseDetailsScreen(course, vm::toggleLesson) { navController.popBackStack() }
        }
    }
}
