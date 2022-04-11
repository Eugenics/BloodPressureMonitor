package com.eugenics.bloodpressuremonitor.ui.compose.Settings

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.ui.compose.theme.HealthColorPalette

@Composable
fun HeartColorsDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = {
                    Text(text = stringResource(R.string.close))
                }
            )
        },
        text = {
            LazyColumn(
                content = {
                    item {
                        HeartColors(
                            iconColor = MaterialTheme.colors.onBackground,
                            systolicText = stringResource(R.string.upper_value),
                            diastolicText = stringResource(R.string.lower_value)
                        )
                        HeartColors(
                            iconColor = HealthColorPalette.Blue,
                            systolicText = "< 100",
                            diastolicText = "< 60"
                        )
                        HeartColors(
                            iconColor = HealthColorPalette.Green,
                            systolicText = "100-129",
                            diastolicText = "60-84"
                        )
                        HeartColors(
                            iconColor = HealthColorPalette.LightGreen,
                            systolicText = "130-139",
                            diastolicText = "85-89"
                        )
                        HeartColors(
                            iconColor = HealthColorPalette.Warning,
                            systolicText = "140-159",
                            diastolicText = "90-99"
                        )
                        HeartColors(
                            iconColor = HealthColorPalette.Alert,
                            systolicText = "> 160",
                            diastolicText = "> 100"
                        )
                    }
                }
            )
        }
    )
}