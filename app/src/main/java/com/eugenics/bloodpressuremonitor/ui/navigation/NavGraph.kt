package com.eugenics.bloodpressuremonitor.ui.navigation

import android.os.Build
import android.window.SplashScreen
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.eugenics.bloodpressuremonitor.BuildConfig
import com.eugenics.bloodpressuremonitor.ui.common.DETAILS_ARGUMENT_KEY
import com.eugenics.bloodpressuremonitor.ui.compose.settings.SettingsScreen
import com.eugenics.bloodpressuremonitor.ui.compose.detail.DetailSreen
import com.eugenics.bloodpressuremonitor.ui.compose.main.MainScreen
import com.eugenics.bloodpressuremonitor.ui.compose.splash.Splash
import com.eugenics.bloodpressuremonitor.ui.viewmodels.AppViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            Screen.Splash.route
        } else {
            Screen.Main.route
        }
    ) {
        composable(
            route = Screen.Main.route,
            enterTransition = {
                fadeIn(animationSpec = tween(700, delayMillis = 400))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700, delayMillis = 400))
            }
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.StringType
            }),
            enterTransition = {
                fadeIn(animationSpec = tween(700, delayMillis = 400))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700, delayMillis = 400))
            }
        ) {
            DetailSreen(
                navController = navController
            )
        }
        composable(route = Screen.Settings.route,
            exitTransition = {
                fadeOut(animationSpec = tween(700, delayMillis = 400))
            }) {
            SettingsScreen(navController = navController)
        }
        composable(route = Screen.Splash.route,
            enterTransition = {
                fadeIn(animationSpec = tween(700, delayMillis = 400))
            },
            exitTransition = {
                fadeOut(animationSpec = tween(700, 400))
            }) {
            Splash(navController)
        }
    }
}