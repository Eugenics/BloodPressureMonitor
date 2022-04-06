package com.eugenics.bloodpressuremonitor.ui.compose.Settings

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.textInputServiceFactory
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eugenics.bloodpressuremonitor.R

@Composable
fun HeartColors(
    iconColor: Color = MaterialTheme.colors.onBackground,
    systolicText: String = "Text",
    diastolicText: String = "Text"
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_baseline_favorite_24),
            contentDescription = stringResource(R.string.heart_rate),
            tint = iconColor,
            modifier = Modifier.padding(5.dp)
                .weight(0.25f)
        )
        Text(
            textAlign = TextAlign.Center,
            text = systolicText,
            style = MaterialTheme.typography.h6,
            fontSize = 19.sp,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .weight(1.0f)
        )
        Text(
            textAlign = TextAlign.Center,
            text = "|",
            style = MaterialTheme.typography.h6,
            fontSize = 19.sp,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .weight(0.25f)
        )
        Text(
            textAlign = TextAlign.Center,
            text = diastolicText,
            style = MaterialTheme.typography.h6,
            fontSize = 19.sp,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .weight(1.0f)
        )
    }
}

@Preview(backgroundColor = 0xFF64dd17, showBackground = true)
@Composable
private fun HeartColorsPreview() {
    HeartColors(
        iconColor = MaterialTheme.colors.onBackground,
        systolicText = stringResource(R.string.upper_value),
        diastolicText = stringResource(R.string.lower_value)
    )
}