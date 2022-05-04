package com.eugenics.bloodpressuremonitor.ui.compose.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.ui.compose.detail.DetailTopBar
import com.eugenics.bloodpressuremonitor.ui.viewmodels.AppViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SettingsScreen(
    navController: NavHostController,
    viewModel: AppViewModel = hiltViewModel()
) {
    var showHeartColorsDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var showLicenseDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    val systemPaddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars
    )

    if (showHeartColorsDialog) {
        HeartColorsDialog { showHeartColorsDialog = false }
    }
    if (showLicenseDialog) {
        SoftwareInfoDialog { showLicenseDialog = false }
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                paddingValues = systemPaddingValues,
                showDeleteButton = false,
                onNavigationButtonClick = { navController.popBackStack() },
                onDeleteButtonClick = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues = paddingValues)
            ) {
                ThemePicker(viewModel = viewModel)
                Divider(modifier = Modifier.padding(5.dp))
                SettingsCard(
                    cardTitleText = stringResource(R.string.info),
                    cardText = stringResource(R.string.color_info),
                    cardTextColor = MaterialTheme.colorScheme.error
                ) {
                    showHeartColorsDialog = true
                }
                SettingsCard(
                    cardTitleText = stringResource(R.string.About),
                    cardText = stringResource(R.string.software_license),
                    cardTextColor = MaterialTheme.colorScheme.error
                ) {
                    showLicenseDialog = true
                }
            }

        }
    )
}

@Preview(backgroundColor = 0xFF64dd17, showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen(navController = rememberNavController())
}