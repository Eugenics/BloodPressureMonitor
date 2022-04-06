package com.eugenics.bloodpressuremonitor.ui.compose.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eugenics.bloodpressuremonitor.ui.navigation.Screen

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val dataList by mainViewModel.dataListStateFlow.collectAsState()
    mainViewModel.fetchData()

    Scaffold(
        topBar = {
            MainTopBar(
                onSettingClick = { navController.navigate(Screen.Settings.route) }
            )
        },
        floatingActionButton = {
            Fab { navController.navigate(Screen.Detail.passMeasureId("0")) }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = {
            Column {
                MainScreenContent(
                    dataList = dataList,
                    navController = navController
                )
            }
        }
    )
}