package com.eugenics.bloodpressuremonitor.data.repository

import com.eugenics.bloodpressuremonitor.data.datasource.local.ILocalDataSource
import com.eugenics.bloodpressuremonitor.data.datasource.settings.ISettingsDataSource
import com.eugenics.bloodpressuremonitor.data.datasource.settings.SettingsDataSource
import com.eugenics.bloodpressuremonitor.domain.IRepository
import javax.inject.Inject

class Repository @Inject constructor(
    private val localDataSource: ILocalDataSource,
    private val settingsDataSource: ISettingsDataSource
) : IRepository {
    override val local: ILocalDataSource = localDataSource
    override val settings: ISettingsDataSource = settingsDataSource
}