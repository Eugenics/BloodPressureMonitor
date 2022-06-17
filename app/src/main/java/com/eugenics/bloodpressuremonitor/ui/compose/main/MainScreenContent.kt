package com.eugenics.bloodpressuremonitor.ui.compose.main

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.compose.theme.HealthColorPalette
import com.eugenics.bloodpressuremonitor.ui.navigation.Screen
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenContent(
    dataList: List<BloodPressureModel>,
    navController: NavHostController
) {
    val gridState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()
    var itemsCount by rememberSaveable { mutableStateOf(dataList.count()) }

    LazyVerticalGrid(
        state = gridState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        columns = GridCells.Adaptive(128.dp),
        // content padding
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 16.dp,
            end = 12.dp,
            bottom = 16.dp
        ),
        content = {
            items(items = dataList, itemContent = { item ->
                BloodPressureCard(item, navController = navController)

                if (dataList.count() != itemsCount) {
                    LaunchedEffect(true) {
                        coroutineScope.launch {
                            gridState.animateScrollToItem(index = 0)
                            itemsCount = dataList.count()
                        }
                    }
                }
            })
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BloodPressureCard(
    data: BloodPressureModel,
    navController: NavHostController
) {
    Card(
        modifier = Modifier
            .padding(5.dp)
            .clickable {
                navController.navigate(Screen.Detail.passMeasureId(data.uid)) {
                    popUpTo(Screen.Main.route)
                }
            }
            .clip(RoundedCornerShape(8.dp))
    ) {
        Column {
            PressureDataText(data.upperValue, data.downValue)
            Row(
                modifier = Modifier.fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HeartImageIcon(getHeartColor(data))
                HeartRateText(data.heartRate)
            }
            DateTimeText(data.measureTime)
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
        navController = rememberNavController()
    )
}

@Composable
private fun PressureDataText(upperValue: Int, downValue: Int) {
    Text(
        text = "${upperValue}/${downValue}",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
private fun HeartImageIcon(heartColor: Color) {
    Icon(
        painter = painterResource(R.drawable.ic_baseline_favorite_24),
        contentDescription = stringResource(R.string.heart_rate),
        tint = heartColor,
        modifier = Modifier
            .padding(top = 10.dp, end = 10.dp)
    )
}

@Composable
private fun HeartRateText(heartRate: Int) {
    Text(
        text = "$heartRate",
        style = MaterialTheme.typography.headlineMedium,
        modifier = Modifier
            .padding(top = 10.dp)
    )
}

@Composable
private fun DateTimeText(dateTime: String) {
    Text(
        text = getMeasureDateTime(dateTime.toLong()),
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

private fun getHeartColor(data: BloodPressureModel): Color {
    val systolic = when {
        data.upperValue < 100 -> 2
        data.upperValue in 100..129 -> 1
        data.upperValue in 130..139 -> 3
        data.upperValue in 140..159 -> 4
        data.upperValue >= 160 -> 5
        else -> 0
    }
    val diastolic = when {
        data.downValue < 60 -> 2
        data.downValue in 60..84 -> 1
        data.downValue in 85..89 -> 3
        data.downValue in 90..99 -> 4
        data.downValue >= 100 -> 5
        else -> 0
    }
    val maxColor = Math.max(systolic, diastolic)

    return when (maxColor) {
        2 -> HealthColorPalette.Blue
        1 -> HealthColorPalette.Green
        3 -> HealthColorPalette.LightGreen
        4 -> HealthColorPalette.Warning
        5 -> HealthColorPalette.Alert
        else -> HealthColorPalette.Green
    }
}
