package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eugenics.bloodpressuremonitor.R


@Composable
fun DeleteDialog(
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = onConfirmButtonClick,
                modifier = Modifier.padding(10.dp),
                content = {
                    Text(text = stringResource(R.string.yes))
                }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismissButtonClick,
                modifier = Modifier.padding(10.dp),
                content = {
                    Text(text = stringResource(R.string.no))
                }
            )
        },
        title = {
            Text(
                text = stringResource(R.string.warning),
                modifier = Modifier.padding(5.dp)
            )
        },
        text = {
            Text(
                text = stringResource(R.string.delete_note),
                modifier = Modifier.padding(5.dp)
            )
        },

        modifier = Modifier
            .padding(5.dp)
    )
}