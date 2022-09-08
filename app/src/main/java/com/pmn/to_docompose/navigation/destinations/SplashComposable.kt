package com.pmn.to_docompose.navigation.destinations

import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pmn.to_docompose.ui.screens.splash.SplashScreen
import com.pmn.to_docompose.util.Constants

@ExperimentalMaterialApi
fun NavGraphBuilder.splashComposable(
    navigateToTaskScreen: () -> Unit
) {
    composable(
        route = Constants.SPLASH_SCREEN
    ) {
        SplashScreen(navigateToTaskScreen = navigateToTaskScreen)
    }
}