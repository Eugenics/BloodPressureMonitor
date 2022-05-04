package com.eugenics.bloodpressuremonitor

import android.app.Application
import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow

@HiltAndroidApp
class BloodMeasureMonitorApp : Application()