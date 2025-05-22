package com.example.todo.navigation.destinations

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.todo.navigation.Screen
import com.example.todo.ui.screens.task.TaskScreen
import com.example.todo.ui.viewmodels.SharedViewModel
import com.example.todo.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit, sharedViewModel: SharedViewModel
) {
    composable<Screen.Task>(
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = 300)
            )
        },
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.toRoute<Screen.Task>().id
        LaunchedEffect(key1 = taskId) {
            sharedViewModel.getSelectedTask(taskId)
        }
        val selectedTask by sharedViewModel.selectedTask.collectAsState()

        LaunchedEffect(selectedTask) {
            if (selectedTask != null || taskId == -1) {
                sharedViewModel.updateTaskFields(selectedTask)
            }
        }

        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask,
            sharedViewModel = sharedViewModel
        )
    }
}