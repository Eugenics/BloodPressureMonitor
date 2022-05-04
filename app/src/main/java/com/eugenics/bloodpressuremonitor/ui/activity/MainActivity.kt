package com.eugenics.bloodpressuremonitor.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.core.view.WindowCompat
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.eugenics.bloodpressuremonitor.ui.compose.app.BloodPressureApp
import com.eugenics.bloodpressuremonitor.ui.compose.theme.BloodPressureMonitorTheme
import com.eugenics.bloodpressuremonitor.ui.viewmodels.AppViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: AppViewModel by viewModels()

    @SuppressLint("StateFlowValueCalledInComposition", "UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val appTheme = viewModel.theme.collectAsState()
            val isDarkMode = mutableStateOf(
                when (appTheme.value) {
                    Theme.DARK -> true
                    Theme.LIGHT -> false
                    else -> isSystemInDarkTheme()
                }
            )
            BloodPressureMonitorTheme(useDarkTheme = isDarkMode.value) { BloodPressureApp() }
        }
    }
}