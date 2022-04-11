package com.eugenics.bloodpressuremonitor.ui.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.data.repository.Repository
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import com.eugenics.bloodpressuremonitor.ui.common.DETAILS_ARGUMENT_KEY
import com.eugenics.bloodpressuremonitor.ui.compose.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.NumberFormatException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MeasureDetailViewModel @Inject constructor(
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,
    application: Application
) : AndroidViewModel(application) {
    var detailUID: String? = null

    @SuppressLint("StaticFieldLeak")
    private val context = application.applicationContext

    private var _viewModelState: MutableStateFlow<DetailState> =
        MutableStateFlow(DetailState.Opened)
    val viewModelState: StateFlow<DetailState> = _viewModelState

    private var _measureNote: MutableStateFlow<BloodPressureModel> =
        MutableStateFlow(createBloodMeasureObject(0, 0, 0))
    val measureNote: StateFlow<BloodPressureModel> = _measureNote

    private var _saveState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val savaState: StateFlow<Boolean>
        get() = _saveState.asStateFlow()

    private var _message: MutableStateFlow<MutableMap<Int, String>> =
        MutableStateFlow(mutableMapOf())
    val message: StateFlow<MutableMap<Int, String>> = _message

    init {
        viewModelScope.launch(Dispatchers.IO) {
            detailUID = savedStateHandle.get<String>(DETAILS_ARGUMENT_KEY)
            detailUID?.let { uid ->
                if (uid != "0") {
                    repository.local.fetchDataById(uid)?.let {
                        _measureNote.value = it
                    }
                }
            }
        }
    }

    fun onEdit(value: String, validateObject: ValidateObject) {
        var preparedValue = 0
        try {
            if (value.trim().isNotBlank()) {
                preparedValue = value.trim().toInt()
                setState(DetailState.Edited)
            }
        } catch (e: NumberFormatException) {
            _message.value = mutableMapOf()
            _message.value[validateObject.index] = "${validateObject.name}: ${e.message}"
            setState(DetailState.Error)
            return
        }

        when (validateObject) {
            ValidateObject.UPPER_VALUE -> {
                _measureNote.value =
                    createBloodMeasureObject(
                        preparedValue,
                        measureNote.value.downValue,
                        measureNote.value.heartRate,
                        measureNote.value.uid,
                        time = measureNote.value.measureTime
                    )
            }
            ValidateObject.LOWER_VALUE -> {
                _measureNote.value =
                    createBloodMeasureObject(
                        measureNote.value.upperValue,
                        preparedValue,
                        measureNote.value.heartRate,
                        measureNote.value.uid,
                        time = measureNote.value.measureTime
                    )
            }
            ValidateObject.HEART_RATE -> {
                _measureNote.value =
                    createBloodMeasureObject(
                        measureNote.value.upperValue,
                        measureNote.value.downValue,
                        preparedValue,
                        measureNote.value.uid,
                        time = measureNote.value.measureTime
                    )
            }
        }
    }

    fun onClose() {
        if (getState() == DetailState.Edited) {
            if (validateMeasure()) {
                saveMeasure()
            } else {
                setState(DetailState.Error)
            }
        } else {
            setState(DetailState.Closing)
        }
    }

    fun setState(state: DetailState) {
        _viewModelState.value = state
    }

    private fun getState(): DetailState = viewModelState.value

    private fun saveMeasure() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertMeasure(measure = measureNote.value)
            setState(DetailState.Closing)
        }
    }

    fun deleteMeasure() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteMeasure(measure = measureNote.value)
            setState(DetailState.Closing)
        }
    }

    private fun validateMeasure(): Boolean {
        validateValue(measureNote.value.upperValue, ValidateObject.UPPER_VALUE)
        validateValue(measureNote.value.downValue, ValidateObject.LOWER_VALUE)
        validateValue(measureNote.value.heartRate, ValidateObject.HEART_RATE)
        return savaState.value
    }

    private fun validateValue(
        value: Int,
        validatedObject: ValidateObject
    ) {
        val errorString: String? = when {
            value == 0 -> context.getString(R.string.required)
            value < validatedObject.min -> {
                context.getString(R.string.low_value)
            }
            value > validatedObject.max -> {
                context.getString(R.string.height_value)
            }
            else -> {
                null
            }
        }
        val errorMessage = errorString?.let { error ->
            "${
                when (validatedObject) {
                    ValidateObject.UPPER_VALUE ->
                        context.getString(R.string.upper_value)
                    ValidateObject.LOWER_VALUE ->
                        context.getString(R.string.lower_value)
                    ValidateObject.HEART_RATE ->
                        context.getString(R.string.heart_rate)
                }
            } - $error"
        } ?: ""

        _saveState.value = errorString != null
        setError(errorMessage = errorMessage, validatedObject.index)
    }

    private fun setError(errorMessage: String, errorIndex: Int) {
        val messageMap = message.value
        if (errorMessage.isBlank()) {
            messageMap.remove(errorIndex)
            if (messageMap.count() == 0) {
                _saveState.value = true
            }
        } else {
            messageMap[errorIndex] = errorMessage
            _saveState.value = false
        }
        _message.value = messageMap
    }

    private fun createBloodMeasureObject(
        upperValue: Int,
        lowerValue: Int,
        heartRate: Int,
        uid: String = UUID.randomUUID().toString(),
        date: String = Calendar.DATE.toString(),
        time: String = Date().time.toString()
    ): BloodPressureModel =
        BloodPressureModel(
            uid = uid,
            upperValue = upperValue,
            downValue = lowerValue,
            heartRate = heartRate,
            measureDate = date,
            measureTime = time
        )

    companion object {
        enum class ValidateObject(val index: Int, val min: Int, val max: Int) {
            UPPER_VALUE(0, 60, 300),
            LOWER_VALUE(1, 30, 240),
            HEART_RATE(2, 40, 200)
        }
    }
}