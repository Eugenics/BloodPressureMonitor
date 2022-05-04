package com.eugenics.bloodpressuremonitor.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eugenics.bloodpressuremonitor.domain.IRepository
import com.eugenics.bloodpressuremonitor.domain.models.Theme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val repository: IRepository
) : ViewModel() {

    val theme: StateFlow<Theme> = repository.settings.appTheme

    fun setSettings(changedTheme: Theme) {
        viewModelScope.launch {
            setThemeSetting(changedTheme)
            Log.d("JSON SET", changedTheme.name)
        }
    }

    private suspend fun setThemeSetting(theme: Theme) {
        repository.settings.setThemeSetting(theme = theme)
    }
}