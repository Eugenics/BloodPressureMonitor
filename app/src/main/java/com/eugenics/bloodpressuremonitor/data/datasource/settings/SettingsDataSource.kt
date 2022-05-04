package com.eugenics.bloodpressuremonitor.data.datasource.settings

import androidx.datastore.core.DataStore
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import com.eugenics.bloodpressuremonitor.domain.models.UserSettings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsDataSource @Inject constructor(private val dataStore: DataStore<UserSettings>) :
    ISettingsDataSource {

    private var _appTheme: MutableStateFlow<Theme> = MutableStateFlow(Theme.SYSTEM)
    override val appTheme: StateFlow<Theme> = _appTheme

    init {
        val scope = CoroutineScope(context = Dispatchers.Main)
        scope.launch {
            _appTheme.value = getThemeSetting()
        }
    }

    override suspend fun getThemeSetting(): Theme =
        dataStore.data.first().themeSetting

    override suspend fun setThemeSetting(theme: Theme) {
        dataStore.updateData { userSettings ->
            userSettings.copy(themeSetting = theme)
        }
        _appTheme.value = theme
    }
}