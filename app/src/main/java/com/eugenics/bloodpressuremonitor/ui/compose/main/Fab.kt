package com.eugenics.bloodpressuremonitor.ui.compose.main

import android.content.res.Configuration
import android.graphics.drawable.GradientDrawable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.eugenics.bloodpressuremonitor.R

@Composable
fun Fab(
    paddingValues: PaddingValues,
    onClick: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val isLandscape = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> true
        else -> false
    }

    val bottomPadding = if (isLandscape) {
        16.dp
    } else {
        paddingValues.calculateBottomPadding() + 16.dp
    }

    val endPadding = if (isLandscape) {
        paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 16.dp
    } else {
        16.dp
    }

    FloatingActionButton(
        onClick = onClick,
        content = {
            Icon(
                painter = painterResource(R.drawable.ic_baseline_favorite_24),
                contentDescription = stringResource(R.string.floating_button),
            )
        },
        modifier = Modifier
            .padding(
                bottom = bottomPadding,
                top = 16.dp,
                start = 16.dp,
                end = endPadding
            )
    )
}