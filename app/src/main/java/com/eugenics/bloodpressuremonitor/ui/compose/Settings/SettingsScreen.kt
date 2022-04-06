package com.eugenics.bloodpressuremonitor.ui.compose.Settings

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.ui.compose.detail.DetailTopBar

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SettingsScreen(
    navController: NavHostController
) {
    var showHeartColorsDialog: Boolean by rememberSaveable { mutableStateOf(false) }
    var showLicenseDialog: Boolean by rememberSaveable { mutableStateOf(false) }

    if (showHeartColorsDialog) {
        HeartColorsDialog { showHeartColorsDialog = false }
    }
    if (showLicenseDialog) {
        SoftwareInfoDialog { showLicenseDialog = false }
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                showDeleteButton = false,
                onNavigationButtonClick = { navController.navigateUp() },
                onDeleteButtonClick = {}
            )
        },
        content = {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "Info",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    fontSize = 16.sp
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = {
                        showHeartColorsDialog = true
                    }
                ) {
                    Text(
                        text = stringResource(R.string.color_info),
                        modifier = Modifier.fillMaxWidth()
                            .padding(10.dp),
                        fontSize = 16.sp
                    )
                }
                Divider(modifier = Modifier.padding(5.dp))
                Text(
                    text = "About",
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.fillMaxWidth()
                        .padding(10.dp),
                    fontSize = 16.sp
                )
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(0.dp, Color.Transparent),
                    onClick = { showLicenseDialog = true }
                ) {
                    Text(
                        text = "Software Licenses",
                        modifier = Modifier.fillMaxWidth()
                            .padding(10.dp),
                        fontSize = 16.sp
                    )
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