package com.pmn.to_docompose.ui.screens.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.pmn.to_docompose.R
import com.pmn.to_docompose.ui.theme.fabBackgroundColor
import com.pmn.to_docompose.ui.viewmodels.SharedViewModel
import com.pmn.to_docompose.util.Action
import com.pmn.to_docompose.util.SearchAppBarState
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
    action: Action
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
        sharedViewModel.readSortState()
    }

    val tasksState by sharedViewModel.allTasks.collectAsState()
    val searchedTasksState by sharedViewModel.searchedTasks.collectAsState()
    val sortState by sharedViewModel.sortState.collectAsState()
    val lowPriorityState by sharedViewModel.lowPriorityTasks.collectAsState()
    val highPriorityState by sharedViewModel.highPriorityTasks.collectAsState()

    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val scaffoldState = rememberScaffoldState()

    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action) },
        onUndoDeleteClick = { sharedViewModel.handleDatabaseActions(it) },
        taskTitle = sharedViewModel.title.value,
        action = action
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListAppBar(
                searchAppBarState = searchAppBarState,
                sharedViewModel = sharedViewModel,
                searchTextState = searchTextState
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen = navigateToTaskScreen)
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            ListContent(
                tasksState = tasksState,
                searchedTasksState = searchedTasksState,
                searchAppBarState = searchAppBarState,
                sortState = sortState,
                lowPriorityTasks = lowPriorityState,
                highPriorityTasks = highPriorityState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.handleDatabaseActions(action)
                    sharedViewModel.updateTaskFields(selectedTask = task)
                },
                navigateToTaskScreen = navigateToTaskScreen
            )
        }
    }
}

@Composable
fun ListFab(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToTaskScreen(-1)
        },
        backgroundColor = MaterialTheme.colors.fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.list_screen_add_button_content_description),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    onUndoDeleteClick: (Action) -> Unit,
    taskTitle: String,
    action: Action
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {

            handleDatabaseActions()

            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = if (action == Action.DELETE) {
                        context.getString(R.string.remove_all_tasks_message)
                    } else {
                        "${action.name}: $taskTitle"
                    },
                    actionLabel = if (action == Action.DELETE) {
                        context.resources.getString(R.string.undo_button)
                    } else {
                        context.resources.getString(R.string.confirm_button)
                    }
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult
                ) {
                    onUndoDeleteClick(it)
                }
            }
        }
    }
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoDeleteClick: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed && action == Action.DELETE) {
        onUndoDeleteClick(Action.UNDO)
    }
}