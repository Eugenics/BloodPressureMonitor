package com.eugenics.bloodpressuremonitor.ui.fragments.detail

import androidx.lifecycle.*
import com.eugenics.bloodpressuremonitor.data.repository.Repository
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MeasureDetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var measure: BloodPressureModel? = null

    private var _measureNote: MutableStateFlow<BloodPressureModel> =
        MutableStateFlow(createBloodMeasureObject(0, 0, 0))
    val measureNote: StateFlow<BloodPressureModel>
        get() = _measureNote.asStateFlow()

    private var _saveState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val savaState: StateFlow<Boolean>
        get() = _saveState.asStateFlow()

    private var _message: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String>
        get() = _message

    fun fetchMeasureById(measureId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.fetchDataById(measureId).collect { data ->
                data?.let {
                    _measureNote.value = data
                }
            }
        }
    }

    fun saveMeasure(
        upperValue: Int,
        lowerValue: Int,
        heartRate: Int
    ) {
        if (measure == null) {
            measure = createBloodMeasureObject(upperValue, lowerValue, heartRate)
        } else {
            measure = updateBloodMeasureObject(upperValue, lowerValue, heartRate)
        }
        viewModelScope.launch(Dispatchers.IO) {
            measure?.let {
                repository.local.insertMeasure(it)
                _message.postValue("Saved...")
                _saveState.value = true
            }
        }
    }

    fun deleteMeasure() {
        viewModelScope.launch(Dispatchers.IO) {
            measure?.let {
                repository.local.deleteMeasure(it)
                _message.postValue("Deleted...")
            }
        }
    }

    private fun createBloodMeasureObject(
        upperValue: Int,
        lowerValue: Int,
        heartRate: Int
    ): BloodPressureModel =
        BloodPressureModel(
            uid = UUID.randomUUID().toString(),
            upperValue = upperValue,
            downValue = lowerValue,
            heartRate = heartRate,
            measureDate = Calendar.DATE.toString(),
            measureTime = Date().time.toString()
        )

    private fun updateBloodMeasureObject(
        upperValue: Int,
        lowerValue: Int,
        heartRate: Int
    ): BloodPressureModel =
        BloodPressureModel(
            uid = measure?.uid ?: UUID.randomUUID().toString(),
            upperValue = upperValue,
            downValue = lowerValue,
            heartRate = heartRate,
            measureDate = measure?.measureDate ?: Calendar.DATE.toString(),
            measureTime = measure?.measureTime ?: Date().time.toString()
        )
}