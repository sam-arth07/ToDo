package com.example.todo.ui.screens.list

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.theme.HighPriorityColor
import com.example.todo.ui.theme.LARGEST_PADDING
import com.example.todo.ui.theme.LARGE_PADDING
import com.example.todo.ui.theme.PRIORITY_INDICATOR_SIZE
import com.example.todo.ui.theme.TASK_ITEM_ELEVATION
import com.example.todo.ui.theme.taskItemTextColor
import com.example.todo.util.Action
import com.example.todo.util.RequestState
import com.example.todo.util.SearchAppBarState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ListContent(
    paddingValues: PaddingValues,
    allTasks: RequestState<List<ToDoTask>>,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    lowPriorityTask: List<ToDoTask>,
    highPriorityTask: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues,
                        onSwipeToDelete = onSwipeToDelete
                    )
                }
            }

            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        paddingValues = paddingValues,
                        onSwipeToDelete = onSwipeToDelete
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(top = paddingValues.calculateTopPadding())
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTask,
                    navigateToTaskScreen = navigateToTaskScreen,
                    paddingValues = paddingValues,
                    onSwipeToDelete = onSwipeToDelete
                )
            }

            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTask,
                    onSwipeToDelete = onSwipeToDelete,
                    navigateToTaskScreen,
                    paddingValues,
                )
            }
        }


    }
}


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    paddingValues: PaddingValues
) {
    if (tasks.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            items(
                items = tasks, key = { task -> task.id }) { task ->
                val dismissState = rememberSwipeToDismissBoxState()
                val dismissDirection = dismissState.dismissDirection
                val isDismissed =
                    dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart && dismissState.progress == 1f
                if (isDismissed && dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                    val scope = rememberCoroutineScope()
                    SideEffect {
                        scope.launch {
                            delay(300)
                            onSwipeToDelete(Action.DELETE, task)
                        }
                    }
                }
                val degrees by animateFloatAsState(
                    targetValue = if (dismissState.progress in 0f..0.5f) 0f else -45f,
                    label = "Degree Animation"
                )
                var itemAppeared by remember { mutableStateOf(false) }
                LaunchedEffect(key1 = true) {
                    itemAppeared = true
                }
                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true,
                    backgroundContent = { RedBackground(degrees = degrees) }) {
                    TaskItem(task, navigateToTaskScreen)
                }
//                AnimatedVisibility(
//                    visible = itemAppeared,
//                    enter = expandVertically(animationSpec = tween(durationMillis = 300)),
//                    exit = shrinkVertically(animationSpec = tween(durationMillis = 300))
//                ) {
//
//                }
            }
        }
    } else {
        EmptyContent()
    }
}

@Composable
fun RedBackground(
    degrees: Float
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING), contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_task),
            tint = MaterialTheme.colorScheme.background
        )
    }
}


@Composable
fun TaskItem(
    toDoTask: ToDoTask, navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RectangleShape,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = { navigateToTaskScreen(toDoTask.id) }) {
        Column(
            modifier = Modifier
                .padding(LARGE_PADDING)
                .fillMaxWidth()
        ) {
            Row {
                Text(
                    modifier = Modifier.weight(8f),
                    text = toDoTask.title,
                    color = MaterialTheme.colorScheme.taskItemTextColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Canvas(
                        modifier = Modifier
                            .width(PRIORITY_INDICATOR_SIZE)
                            .height(
                                PRIORITY_INDICATOR_SIZE
                            )
                    ) {
                        drawCircle(color = toDoTask.priority.color)
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = toDoTask.description,
                color = MaterialTheme.colorScheme.taskItemTextColor,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
