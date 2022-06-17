package com.eugenics.bloodpressuremonitor.ui.compose.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.eugenics.bloodpressuremonitor.ui.viewmodels.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemePicker(
    viewModel: AppViewModel
) {
    val currentTheme  = viewModel.theme.collectAsState()
    val showThemeDialog = remember { mutableStateOf(false) }
    val themeName = remember { mutableStateOf(" ") }

    themeName.value = when (currentTheme.value) {
        Theme.DARK -> stringResource(R.string.dark)
        Theme.LIGHT -> stringResource(R.string.light)
        else -> stringResource(R.string.system)
    }

    if (showThemeDialog.value) {
        ThemeChooseDialog(
            currentTheme = currentTheme.value,
            onThemeChoose = {
                viewModel.setSettings(it)
                showThemeDialog.value = false
            }
        ) {
            showThemeDialog.value = false
        }
    }

    Text(
        text = stringResource(R.string.settings),
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp),
        style = MaterialTheme.typography.bodyLarge
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        border = BorderStroke(0.dp, Color.Transparent),
        onClick = { showThemeDialog.value = true }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(R.string.theme),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(10.dp)
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterStart)
            )
            Text(
                text = themeName.value,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(10.dp)
                    .weight(1f)
                    .wrapContentSize(align = Alignment.CenterEnd)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThemeChooseDialog(
    currentTheme: Theme = Theme.SYSTEM,
    onThemeChoose: (theme: Theme) -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissButtonClick,
        title = {
            Text(
                text = stringResource(R.string.choose_theme),
                style = MaterialTheme.typography.titleLarge
            )
        },
        dismissButton = {
            Button(
                onClick = onDismissButtonClick,
            ) {
                Text(text = stringResource(R.string.close))
            }
        },
        confirmButton = {},
        text = {
            Column(modifier = Modifier.selectableGroup()) {
                for (theme in Theme.values()) {
                    Card(
                        onClick = { onThemeChoose(theme) },
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                        border = BorderStroke(0.dp, Color.Transparent),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentTheme == theme,
                                enabled = true,
                                onClick = { onThemeChoose(theme) }
                            )
                            Text(
                                text = when (theme) {
                                    Theme.DARK -> stringResource(R.string.dark)
                                    Theme.LIGHT -> stringResource(R.string.light)
                                    else -> stringResource(R.string.system)
                                },
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
private fun ThemePickerPreview() {
    ThemePicker(hiltViewModel())
}