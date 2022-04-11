package com.eugenics.bloodpressuremonitor.ui.compose.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eugenics.bloodpressuremonitor.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("ResourceType")
@Composable
fun UpperValueTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        textStyle = TextStyle(fontSize = 26.sp),
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.upper_value))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    )
}

@SuppressLint("ResourceType")
@Composable
fun DownValueTextField(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        textStyle = TextStyle(fontSize = 26.sp),
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.lower_value))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Next
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    )
}

@SuppressLint("ResourceType")
@Composable
fun HeartRateTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDoneAction: (KeyboardActionScope) -> Unit
) {
    OutlinedTextField(
        value = value,
        maxLines = 1,
        textStyle = TextStyle(fontSize = 26.sp),
        onValueChange = onValueChange,
        label = {
            Text(text = stringResource(R.string.heart_rate))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = onDoneAction),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    )
}

@Preview(backgroundColor = 0xFF00FF00, showBackground = true)
@Composable
fun TextFieldsUpperPreview() {
    UpperValueTextField("120") {}
}

@Preview(backgroundColor = 0xFF00FF00, showBackground = true)
@Composable
fun TextFieldsLowerPreview() {
    DownValueTextField("80") {}
}

@Preview(backgroundColor = 0xFF00FF00, showBackground = true)
@Composable
fun TextFieldsHeartRatePreview() {
    HeartRateTextField("60", {}) {}
}