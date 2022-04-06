package com.eugenics.bloodpressuremonitor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eugenics.bloodpressuremonitor.ui.common.DETAILS_ARGUMENT_KEY
import com.eugenics.bloodpressuremonitor.ui.compose.Settings.SettingsScreen
import com.eugenics.bloodpressuremonitor.ui.compose.detail.DetailSreen
import com.eugenics.bloodpressuremonitor.ui.compose.main.MainScreen

@Composable
fun SetUpNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            })
        ) {
            //            val uid = entry.arguments?.getString(DETAILS_ARGUMENT_KEY)
            DetailSreen(
                navController = navController
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}