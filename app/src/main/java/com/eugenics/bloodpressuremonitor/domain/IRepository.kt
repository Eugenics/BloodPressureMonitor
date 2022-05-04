package com.eugenics.bloodpressuremonitor.domain

import com.eugenics.bloodpressuremonitor.data.datasource.settings.ISettingsDataSource

interface IRepository {
    val local: IDataSource
    val settings: ISettingsDataSource
}