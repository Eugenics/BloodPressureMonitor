package com.eugenics.bloodpressuremonitor.data.datasource.settings

import com.eugenics.bloodpressuremonitor.domain.models.Theme
import kotlinx.coroutines.flow.StateFlow

interface ISettingsDataSource {
    val appTheme: StateFlow<Theme>

    suspend fun getThemeSetting(): Theme
    suspend fun setThemeSetting(theme: Theme)
}