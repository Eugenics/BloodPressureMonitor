package com.eugenics.bloodpressuremonitor.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.room.Room
import com.eugenics.bloodpressuremonitor.BloodMeasureMonitorApp
import com.eugenics.bloodpressuremonitor.data.database.BloodPressureMonitorDataBase
import com.eugenics.bloodpressuremonitor.domain.models.UserSettings
import com.eugenics.bloodpressuremonitor.domain.models.UserSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
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

    @Provides
    @Singleton
    fun providesDataStore(application: Application): DataStore<UserSettings> =
        DataStoreFactory.create(
            serializer = UserSettingsSerializer,
            produceFile = {
                File(application.filesDir, "datastore/user_settings.preferences_pb")
            }
        )

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext app: Context): BloodMeasureMonitorApp =
        app as BloodMeasureMonitorApp
}