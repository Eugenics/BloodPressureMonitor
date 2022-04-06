package com.eugenics.bloodpressuremonitor.data.datasource.local

import com.eugenics.bloodpressuremonitor.data.database.BloodPressureMonitorDataBase
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dataBase: BloodPressureMonitorDataBase
) : ILocalDataSource {
    override suspend fun fetchData(): List<BloodPressureModel> =
        dataBase.bloodPressureDao.fetchAllData()

    override suspend fun fetchDataById(measureId: String): BloodPressureModel? =
        dataBase.bloodPressureDao.fetchDataById(measureId)

    override suspend fun insertMeasure(measure: BloodPressureModel) {
        dataBase.bloodPressureDao.insert(measure)
    }

    override suspend fun updateMeasure(measure: BloodPressureModel) {
        dataBase.bloodPressureDao.update(measure)
    }

    override suspend fun deleteMeasure(measure: BloodPressureModel) {
        dataBase.bloodPressureDao.delete(measure)
    }
}