package com.eugenics.bloodpressuremonitor.data.datasource.settings

import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.eugenics.bloodpressuremonitor.domain.models.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISettingsDataSource {
    val appTheme: StateFlow<Theme>

    suspend fun getSettings(): Flow<UserSettings>
    suspend fun setSettings(settings: UserSettings)

    suspend fun getThemeSetting(): Theme
    suspend fun setThemeSetting(theme: Theme)
}