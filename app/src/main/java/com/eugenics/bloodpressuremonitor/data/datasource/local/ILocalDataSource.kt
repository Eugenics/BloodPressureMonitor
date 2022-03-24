package com.eugenics.bloodpressuremonitor.data.datasource.local

import com.eugenics.bloodpressuremonitor.domain.IDataSource
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ILocalDataSource : IDataSource {
    suspend fun fetchData(): Flow<List<BloodPressureModel>>
    suspend fun fetchDataById(measureId: String): Flow<BloodPressureModel?>
    suspend fun insertMeasure(measure: BloodPressureModel)
    suspend fun deleteMeasure(measure: BloodPressureModel)
}