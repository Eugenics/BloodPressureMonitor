package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eugenics.bloodpressuremonitor.R

@Composable
fun DetailTopBar(
    showDeleteButton: Boolean = false,
    onNavigationButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton(
                onClick = onNavigationButtonClick,
                content = {
                    Icon(
                        painter = painterResource(R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = stringResource(R.string.back)
                    )
                }
            )
        },
        actions = {
            if (showDeleteButton) {
                IconButton(
                    onClick = onDeleteButtonClick,
                    content = {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_delete_forever_24),
                            contentDescription = stringResource(R.string.delete)
                        )
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun DetailTopBarPreview() {
    DetailTopBar(true, {}, {})
}