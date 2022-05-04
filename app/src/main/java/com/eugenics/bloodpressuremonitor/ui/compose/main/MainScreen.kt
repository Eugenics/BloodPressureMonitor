package com.eugenics.bloodpressuremonitor.ui.compose.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eugenics.bloodpressuremonitor.ui.navigation.Screen
import com.eugenics.bloodpressuremonitor.ui.viewmodels.MainViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val dataList by mainViewModel.dataListStateFlow.collectAsState()
    val mainState by mainViewModel.mainState.collectAsState()
    val fabState by mainViewModel.fabVisibleState.collectAsState()

    val systemPaddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars
    )

    val swipeRefreshState = rememberSwipeRefreshState(mainState is MainState.Refresh)

    mainViewModel.fetchData()

    Scaffold(
        topBar = {
            MainTopBar(
                paddingValues = systemPaddingValues,
                onSettingClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = fabState,
                enter = fadeIn(animationSpec = tween(700, delayMillis = 250)),
                exit = fadeOut(animationSpec = tween(250, delayMillis = 250))
            ) {
                Fab(paddingValues = systemPaddingValues) {
                    navController.navigate(Screen.Detail.passMeasureId("0"))
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        content = { paddingValues ->
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = {
                    mainViewModel.refreshData()
                },
                modifier = Modifier.padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding() + 30.dp
                )
            ) {
                Column {
                    MainScreenContent(
                        dataList = dataList,
                        navController = navController
                    )
                }
            }

        }
    )
}