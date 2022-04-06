package com.eugenics.bloodpressuremonitor.data.datasource.local

import com.eugenics.bloodpressuremonitor.domain.IDataSource
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ILocalDataSource : IDataSource {
    suspend fun fetchData(): List<BloodPressureModel>
    suspend fun fetchDataById(measureId: String): BloodPressureModel?
    suspend fun insertMeasure(measure: BloodPressureModel)
    suspend fun updateMeasure(measure: BloodPressureModel)
    suspend fun deleteMeasure(measure: BloodPressureModel)
}