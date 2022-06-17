package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.eugenics.bloodpressuremonitor.R
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@Composable
fun DetailTopBar(
    paddingValues: PaddingValues,
    showDeleteButton: Boolean = false,
    onNavigationButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit
) {
    SmallTopAppBar(
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
        },
        modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
    )
}

@Preview
@Composable
fun DetailTopBarPreview() {
    DetailTopBar(
        rememberInsetsPaddingValues(
            insets = LocalWindowInsets.current.systemBars
        ),
        true, {}, {})
}