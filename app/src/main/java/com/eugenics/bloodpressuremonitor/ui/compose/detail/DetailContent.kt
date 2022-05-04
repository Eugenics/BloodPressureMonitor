package com.eugenics.bloodpressuremonitor.ui.compose.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.ui.viewmodels.MeasureDetailViewModel
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailContent(
    navController: NavHostController,
    viewModel: MeasureDetailViewModel = hiltViewModel()
) {
    val errorMessage by viewModel.message.collectAsState()
    val viewModelState by viewModel.viewModelState.collectAsState()

    var upperValue = viewModel.measureNote.collectAsState().value.upperValue.toString()
    var lowerValue = viewModel.measureNote.collectAsState().value.downValue.toString()
    var heartRateValue = viewModel.measureNote.collectAsState().value.heartRate.toString()

    val keyboardControl = LocalSoftwareKeyboardController.current

    val systemPaddingValues = rememberInsetsPaddingValues(
        insets = LocalWindowInsets.current.systemBars
    )

    if (viewModelState == DetailState.Error) {
        ErrorAlert(
            errorMessage
        ) {
            viewModel.setState(DetailState.Edited)
        }
    }

    if (viewModelState == DetailState.Delete) {
        DeleteDialog(
            { viewModel.deleteMeasure() },
            { viewModel.setState(DetailState.Edited) }
        )
    }

    if (viewModelState == DetailState.Closing) {
        keyboardControl?.hide()
        navController.navigateUp()
    }

    Scaffold(
        topBar = {
            DetailTopBar(
                paddingValues = systemPaddingValues,
                onNavigationButtonClick = {
                    viewModel.setState(DetailState.Closing)
                },
                onDeleteButtonClick = {
                    viewModel.setState(DetailState.Delete)
                },
                showDeleteButton = viewModel.detailUID != "0"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(paddingValues)
        ) {
            UpperValueTextField(checkValue(upperValue)) {
                upperValue = it.trim()
                viewModel.onEdit(
                    it.trim(),
                    MeasureDetailViewModel.Companion.ValidateObject.UPPER_VALUE
                )
            }
            DownValueTextField(checkValue(lowerValue)) {
                lowerValue = it.trim()
                viewModel.onEdit(
                    it.trim(),
                    MeasureDetailViewModel.Companion.ValidateObject.LOWER_VALUE
                )
            }
            HeartRateTextField(checkValue(heartRateValue),
                {
                    heartRateValue = it.trim()
                    viewModel.onEdit(
                        it.trim(),
                        MeasureDetailViewModel.Companion.ValidateObject.HEART_RATE
                    )
                }
            ) {
                viewModel.onClose()
            }
            Button(
                onClick = { viewModel.onClose() },
                content = {
                    Text(
                        text = stringResource(R.string.save),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(5.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    }
}

private fun checkValue(value: String): String =
    if (value.trim() == "0") {
        ""
    } else {
        value.trim()
    }