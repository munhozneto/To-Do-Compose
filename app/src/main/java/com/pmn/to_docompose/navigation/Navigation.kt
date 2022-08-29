package com.pmn.to_docompose.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.pmn.to_docompose.navigation.destinations.listComposable
import com.pmn.to_docompose.navigation.destinations.taskComposable
import com.pmn.to_docompose.ui.viewmodels.SharedViewModel
import com.pmn.to_docompose.util.Constants.LIST_SCREEN

@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(navController = navController, startDestination = LIST_SCREEN) {
        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable(
            navigateToListScreen = screen.list
        )
    }
}