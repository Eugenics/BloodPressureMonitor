package com.eugenics.bloodpressuremonitor.ui.compose.app

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.eugenics.bloodpressuremonitor.ui.compose.theme.BloodPressureMonitorTheme
import com.eugenics.bloodpressuremonitor.ui.navigation.SetUpNavGraph
import com.eugenics.bloodpressuremonitor.ui.viewmodels.AppViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BloodPressureApp(
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController: NavHostController = rememberAnimatedNavController()
    val theme: Theme by viewModel.theme.collectAsState()

    val useDarkTheme = when (theme) {
        Theme.DARK -> true
        Theme.LIGHT -> false
        else -> isSystemInDarkTheme()
    }

    ProvideWindowInsets {
        SetUpNavGraph(navController = navController)

    }
}