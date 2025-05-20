package com.example.todo.navigation.destinations

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.todo.navigation.Screen
import com.example.todo.ui.screens.task.TaskScreen
import com.example.todo.ui.viewmodels.SharedViewModel
import com.example.todo.util.Action

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit, sharedViewModel: SharedViewModel
) {
    composable<Screen.Task> { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt("id")
        sharedViewModel.getSelectedTask(taskId)
        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        TaskScreen(
            navigateToListScreen = navigateToListScreen,
            selectedTask = selectedTask
        )
    }
}