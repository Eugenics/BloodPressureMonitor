package com.eugenics.bloodpressuremonitor.data.database.dao

import androidx.room.*
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BloodPressureDao {
    @Query("SELECT * FROM bloodPressure ORDER BY measureTime DESC")
    fun fetchAllData(): List<BloodPressureModel>

    @Query("SELECT * FROM bloodPressure WHERE uid = :measureId")
    fun fetchDataById(measureId: String): BloodPressureModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bloodPressureObject: BloodPressureModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(bloodPressureObject: BloodPressureModel)

    @Delete
    fun delete(bloodPressureObject: BloodPressureModel)
}