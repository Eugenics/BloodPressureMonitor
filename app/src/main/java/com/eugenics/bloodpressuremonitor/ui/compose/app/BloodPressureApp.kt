package com.eugenics.bloodpressuremonitor.ui.compose.app

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.eugenics.bloodpressuremonitor.ui.navigation.SetUpNavGraph
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun BloodPressureApp(navController: NavHostController) {
    ProvideWindowInsets {
        SetUpNavGraph(navController = navController)
    }
}