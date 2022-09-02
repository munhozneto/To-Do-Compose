package com.pmn.to_docompose.ui.screens.task

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.pmn.to_docompose.R
import com.pmn.to_docompose.data.models.Priority
import com.pmn.to_docompose.data.models.ToDoTask
import com.pmn.to_docompose.ui.viewmodels.SharedViewModel
import com.pmn.to_docompose.util.Action

@Composable
fun TaskScreen(
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel,
    navigationToListScreen: (Action) -> Unit
) {
    val title: String by sharedViewModel.title
    val description: String by sharedViewModel.description
    val priority: Priority by sharedViewModel.priority

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask
            ) { action ->
                if (action == Action.NO_ACTION) {
                    navigationToListScreen(action)
                } else {
                    if (sharedViewModel.validateFields()) {
                        navigationToListScreen(action)
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.empty_fields_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        },
        content = { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                TaskContent(
                    title = title,
                    onTitleChange = {
                        sharedViewModel.updateTitle(it)
                    },
                    description = description,
                    onDescriptionChange = {
                        sharedViewModel.description.value = it
                    },
                    priority = priority,
                    onPrioritySelect = {
                        sharedViewModel.priority.value = it
                    }
                )
            }
        }
    )
}