package com.eugenics.bloodpressuremonitor.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eugenics.bloodpressuremonitor.data.database.dao.BloodPressureDao
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel

@Database(
    entities = [BloodPressureModel::class],
    version = 1,
    exportSchema = true
)
abstract class BloodPressureMonitorDataBase : RoomDatabase() {
    abstract val bloodPressureDao: BloodPressureDao
}