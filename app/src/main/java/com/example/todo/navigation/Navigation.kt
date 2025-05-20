package com.example.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todo.navigation.destinations.listComposable
import com.example.todo.navigation.destinations.taskComposable
import com.example.todo.ui.viewmodels.SharedViewModel

@Composable
fun Navigation(
    navController: NavHostController, sharedViewModel: SharedViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List()
    ) {
        listComposable(
            navigateToTaskScreen = { taskId ->
                navController.navigate(Screen.Task(id = taskId))
            },
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = { action ->
                navController.navigate(Screen.List(action = action)) {
                    popUpTo(Screen.List()) { inclusive = true }
                }
            },
            sharedViewModel = sharedViewModel
        )
    }
}