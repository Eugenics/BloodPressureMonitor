package com.eugenics.bloodpressuremonitor.di

import com.eugenics.bloodpressuremonitor.data.datasource.local.ILocalDataSource
import com.eugenics.bloodpressuremonitor.data.datasource.local.LocalDataSource
import com.eugenics.bloodpressuremonitor.data.datasource.settings.ISettingsDataSource
import com.eugenics.bloodpressuremonitor.data.datasource.settings.SettingsDataSource
import com.eugenics.bloodpressuremonitor.data.repository.Repository
import com.eugenics.bloodpressuremonitor.domain.IRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface BindsDataModule {
    @Binds
    @Singleton
    fun bindLocalDataSource(localDataSource: LocalDataSource): ILocalDataSource

    @Binds
    @Singleton
    fun bingSettingsDataSource(settingsDataSource: SettingsDataSource): ISettingsDataSource
}