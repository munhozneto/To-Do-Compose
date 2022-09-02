package com.pmn.to_docompose.ui.screens.task

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.pmn.to_docompose.R
import com.pmn.to_docompose.data.models.ToDoTask
import com.pmn.to_docompose.ui.theme.topAppBarBackgroundColor
import com.pmn.to_docompose.ui.theme.topAppBarContentColor
import com.pmn.to_docompose.util.Action

@Composable
fun TaskAppBar(
    selectedTask: ToDoTask?,
    navigationToListScreen: (Action) -> Unit
) {
    selectedTask?.let { task ->
        ExistingTaskAppBar(selectedTask = task, navigationToListScreen = navigationToListScreen)
    } ?: run {
        NewTaskAppBar(navigationToListScreen = navigationToListScreen)
    }
}

@Composable
fun NewTaskAppBar(
    navigationToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClick = navigationToListScreen)
        },
        title = {
            Text(
                text = stringResource(R.string.task_app_bar_add_task_button),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            AddAction(onAddClick = navigationToListScreen)
        }
    )
}

@Composable
fun BackAction(
    onBackClick: (Action) -> Unit
) {
    IconButton(onClick = {
        onBackClick(Action.NO_ACTION)
    }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_content_description),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddClick: (Action) -> Unit
) {
    IconButton(onClick = {
        onAddClick(Action.ADD)
    }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.task_app_bar_add_task_button),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun ExistingTaskAppBar(
    selectedTask: ToDoTask,
    navigationToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            CloseAction(onCloseClick = navigationToListScreen)
        },
        title = {
            Text(
                text = selectedTask.title,
                color = MaterialTheme.colors.topAppBarContentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            DeleteAction(onDeleteClick = navigationToListScreen)
            UpdateAction(onUpdateClick = navigationToListScreen)
        }
    )
}

@Composable
fun CloseAction(
    onCloseClick: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClick(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(R.string.close_label),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClick: (Action) -> Unit
) {
    IconButton(onClick = {
        onDeleteClick(Action.DELETE)
    }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_label),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClick: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClick(Action.UPDATE) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.update_label),
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

