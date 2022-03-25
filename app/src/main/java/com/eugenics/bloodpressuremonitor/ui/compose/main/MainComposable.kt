package com.eugenics.bloodpressuremonitor.ui.compose.main

import android.view.View
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.fragments.main.MainViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MainJornal(
    viewModel: MainViewModel,
    onCardClick: (measure: BloodPressureModel) -> Unit
) {
    val dataList by viewModel.dataListStateFlow.collectAsState()
    MainJornalContent(dataList = dataList, onCardClick = onCardClick)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainJornalContent(
    dataList: List<BloodPressureModel>,
    onCardClick: (measure: BloodPressureModel) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        cells = GridCells.Adaptive(128.dp),

        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(items = dataList, itemContent = { item ->
                BloodPressureCard(item, onCardClick = onCardClick)
            })
        }
    )
}

@Composable
private fun BloodPressureCard(
    data: BloodPressureModel,
    onCardClick: (measure: BloodPressureModel) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                onCardClick(data)
            }
    ) {
        Column {
            PressureData(data.upperValue, data.downValue)
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeartImage()
                HeartRate(data.heartRate)
            }
            DateTimeValue(data.measureTime)
        }
    }
}

@Preview
@Composable
private fun BloodPressureCardPreview() {
    BloodPressureCard(
        BloodPressureModel(
            uid = "",
            upperValue = 120,
            downValue = 80,
            heartRate = 60,
            measureDate = "24.03.2022",
            measureTime = "849885575"
        ),
        {}
    )
}

@Composable
private fun PressureData(upperValue: Int, downValue: Int) {
    Text(
        text = "${upperValue}/${downValue}",
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun HeartImage() {
    Image(
        painterResource(R.drawable.ic_baseline_heart_24_small),
        stringResource(R.string.heart_rate),
        modifier = Modifier
            .padding(top = 10.dp)
    )
}

@Composable
private fun HeartRate(heartRate: Int) {
    Text(
        text = "$heartRate",
        style = MaterialTheme.typography.h4,
        modifier = Modifier
            .padding(top = 10.dp)
    )
}

@Composable
private fun DateTimeValue(dateTime: String) {
    Text(
        text = getMeasureDateTime(dateTime.toLong()),
//        style = MaterialTheme.typography.h6,
        fontSize = 14.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

private fun getMeasureDateTime(timeInMillis: Long): String {
    val dateFormat: DateFormat =
        SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val measureDate = calendar.time
    return dateFormat.format(measureDate)
}
