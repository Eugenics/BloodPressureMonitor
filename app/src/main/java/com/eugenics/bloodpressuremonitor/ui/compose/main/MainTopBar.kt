package com.eugenics.bloodpressuremonitor.ui.compose.main

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eugenics.bloodpressuremonitor.R

@Composable
fun MainTopBar(
    onSettingClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        actions = {
            IconButton(onClick = onSettingClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        }
    )
}

@Preview
@Composable
fun MainTopBarPreview() {
    MainTopBar {}
}