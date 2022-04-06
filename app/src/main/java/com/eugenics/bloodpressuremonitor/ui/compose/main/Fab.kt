package com.eugenics.bloodpressuremonitor.ui.compose.main

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.eugenics.bloodpressuremonitor.R

@Composable
fun Fab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_favorite_24),
                contentDescription = stringResource(R.string.floating_button),
            )
        }
    )
}