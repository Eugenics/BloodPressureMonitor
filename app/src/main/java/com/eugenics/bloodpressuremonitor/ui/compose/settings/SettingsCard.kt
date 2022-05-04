package com.eugenics.bloodpressuremonitor.ui.compose.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eugenics.bloodpressuremonitor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsCard(
    cardTitleText: String,
    cardText: String,
    cardTextColor: Color,
    onCardClick: () -> Unit
) {
    Text(
        text = cardTitleText,
        color = cardTextColor,
        modifier = Modifier.fillMaxWidth()
            .padding(5.dp),
        style = MaterialTheme.typography.bodyLarge
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp),
        border = BorderStroke(0.dp, Color.Transparent),
        onClick = onCardClick,
    ) {
        Text(
            text = cardText,
            modifier = Modifier.fillMaxWidth()
                .padding(10.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}