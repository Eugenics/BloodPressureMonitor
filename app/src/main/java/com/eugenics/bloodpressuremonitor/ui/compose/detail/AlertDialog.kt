package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eugenics.bloodpressuremonitor.R

@Composable
fun ErrorAlert(
    errors: Map<Int, String>,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        dismissButton = {
            Button(
                onClick = onDismissButtonClick,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = stringResource(R.string.close),
                    modifier = Modifier.padding(5.dp)
                )
            }
        },
        title = {
            Text(text = stringResource(R.string.warning))
        },
        text = {
            Column {
                for (message in errors) {
                    Text(
                        text = message.value,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .padding(5.dp)
                    )
                }
            }
        },
        modifier = Modifier.padding(5.dp)
    )
}