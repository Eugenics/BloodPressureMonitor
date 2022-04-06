package com.eugenics.bloodpressuremonitor.ui.compose.main


import androidx.lifecycle.*
import com.eugenics.bloodpressuremonitor.data.repository.Repository
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _dataListStateFlow: MutableStateFlow<List<BloodPressureModel>> =
        MutableStateFlow(listOf())
    val dataListStateFlow: StateFlow<List<BloodPressureModel>> = _dataListStateFlow


    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _dataListStateFlow.value = repository.local.fetchData()
        }
    }

    fun insertData(){
        viewModelScope.launch(Dispatchers.IO) {
            insertTestData()
        }
    }
    private suspend fun insertTestData() {
        val measures: MutableList<BloodPressureModel> = mutableListOf(
            BloodPressureModel(
                uid = UUID.randomUUID().toString(),
                upperValue = 120,
                downValue = 70,
                heartRate = 60,
                measureDate = Calendar.DATE.toString(),
                measureTime = Date().time.toString()
            ),
            BloodPressureModel(
                uid = UUID.randomUUID().toString(),
                upperValue = 130,
                downValue = 80,
                heartRate = 65,
                measureDate = Calendar.DATE.toString(),
                measureTime = Date().time.toString()
            ),
            BloodPressureModel(
                uid = UUID.randomUUID().toString(),
                upperValue = 100,
                downValue = 90,
                heartRate = 74,
                measureDate = Calendar.DATE.toString(),
                measureTime = Date().time.toString()
            ),
        )

        measures.forEach { measure ->
            repository.local.insertMeasure(measure)
        }
    }
}