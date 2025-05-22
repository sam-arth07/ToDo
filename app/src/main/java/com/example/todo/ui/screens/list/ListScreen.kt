package com.example.todo.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.example.todo.R
import com.example.todo.ui.viewmodels.SharedViewModel
import com.example.todo.util.Action
import com.example.todo.util.SearchAppBarState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListScreen(
    navigateToTaskScreen: (taskId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    action: Action
) {
    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action)
    }

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityTask by sharedViewModel.lowPriorityTask.collectAsState()
    val highPriorityTask by sharedViewModel.highPriorityTask.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val snackBarHost = remember {
        SnackbarHostState()
    }
    DisplaySnackBar(
        snackBarHost = snackBarHost,
        onComplete = { sharedViewModel.action.value = it },
        taskTitle = sharedViewModel.title.value,
        action = action,
        onUndoClicked = { action ->
            sharedViewModel.action.value = action
        })
    Scaffold(snackbarHost = { SnackbarHost(snackBarHost) }, topBar = {
        ListAppBar(
            sharedViewModel = sharedViewModel,
            searchAppBarState = searchAppBarState,
            searchTextState = searchTextState
        )
    }, floatingActionButton = {
        ListFAB(onFabClicked = navigateToTaskScreen)
    }, content = { paddingValues ->
        ListContent(
            paddingValues = paddingValues,
            allTasks = allTasks,
            navigateToTaskScreen = navigateToTaskScreen,
            searchAppBarState = searchAppBarState,
            searchedTasks = searchedTasks,
            lowPriorityTask = lowPriorityTask,
            highPriorityTask = highPriorityTask,
            sortState = sortState,
            onSwipeToDelete = { action, task ->
                sharedViewModel.action.value = action
                sharedViewModel.updateTaskFields(selectedTask = task)
                snackBarHost.currentSnackbarData?.dismiss()
            }
        )
    })
}

@Composable
fun ListFAB(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        }, containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun DisplaySnackBar(
    snackBarHost: SnackbarHostState,
    onComplete: (Action) -> Unit,
    taskTitle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit
) {
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            val snackBarResult = snackBarHost.showSnackbar(
                message = setMessage(action, taskTitle),
                actionLabel = setActionLabel(action),
                duration = SnackbarDuration.Short
            )
            if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
                onUndoClicked(Action.UNDO)
            } else if (snackBarResult == SnackbarResult.Dismissed || action != Action.DELETE) {
                onComplete(Action.NO_ACTION)
            }
        }
    }
}

private fun setMessage(
    action: Action,
    taskTitle: String
): String {
    return when (action) {
        Action.DELETE_ALL -> {
            "All Tasks Removed."
        }

        else -> {
            "${action.name} : $taskTitle"
        }
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") {
        "UNDO"
    } else {
        "OK"
    }
}