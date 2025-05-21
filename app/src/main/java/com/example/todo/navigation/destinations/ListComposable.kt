package com.example.todo.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.todo.navigation.Screen
import com.example.todo.ui.screens.list.ListScreen
import com.example.todo.ui.viewmodels.SharedViewModel
import com.example.todo.util.Action

fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable<Screen.List> { navBackStackEntry ->
        val action = navBackStackEntry.arguments?.get("action") as Action
        var myAction by rememberSaveable { mutableStateOf(Action.NO_ACTION) }
        LaunchedEffect(key1 = myAction) {
            if (action != myAction) {
                myAction = action
                sharedViewModel.action.value = action
            }
        }
        val databaseAction by sharedViewModel.action
        ListScreen(
            navigateToTaskScreen = navigateToTaskScreen,
            sharedViewModel = sharedViewModel,
            action = databaseAction
        )
    }
}