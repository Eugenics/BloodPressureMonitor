package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun DetailSreen(
    navController: NavHostController,
    viewModel: MeasureDetailViewModel = hiltViewModel()
) {
    DetailContent(
        navController = navController,
        viewModel = viewModel
    )
}
