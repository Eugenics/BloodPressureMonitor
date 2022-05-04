package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eugenics.bloodpressuremonitor.R

@Composable
fun ErrorAlert(
    errors: Map<Int, String>,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissButtonClick,
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