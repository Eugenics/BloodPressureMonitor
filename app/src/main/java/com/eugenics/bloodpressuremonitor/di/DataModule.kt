package com.eugenics.bloodpressuremonitor.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.eugenics.bloodpressuremonitor.data.database.BloodPressureMonitorDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun providePersistsDataBase(application: Application): BloodPressureMonitorDataBase =
        Room.databaseBuilder(
            application,
            BloodPressureMonitorDataBase::class.java,
            "local_db.db"
        )
            .build()
}