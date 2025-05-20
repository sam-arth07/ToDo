package com.example.todo.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.viewmodels.SharedViewModel
import com.example.todo.util.Action

@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(topBar = {
        TaskAppBar(
            selectedTask = selectedTask,
            navigateToListScreen = { action ->
                if (action == Action.NO_ACTION) {
                    navigateToListScreen(action)
                } else {
                    if (sharedViewModel.validateFields()) {
                        navigateToListScreen(action)
                    } else {
                        displayToast(context = context)
                    }
                }
            }
        )
    }, content = { paddingValues ->
        TaskContent(
            paddingValues = paddingValues,
            title = title,
            onTitleChange = { newTitle ->
                sharedViewModel.updateTitle(newTitle)
            },
            description = description,
            onDescriptionChange = { newDesc ->
                sharedViewModel.description.value = newDesc
            },
            priority = priority,
            onPrioritySelected = { newPriority ->
                sharedViewModel.priority.value = newPriority
            })
    })
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Fields Empty.",
        Toast.LENGTH_SHORT
    ).show()
}