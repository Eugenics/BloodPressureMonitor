package com.eugenics.bloodpressuremonitor.ui.fragments.main.adapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eugenics.bloodpressuremonitor.databinding.BloodPressureCardBinding
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding: BloodPressureCardBinding by viewBinding()

    @SuppressLint("SetTextI18n")
    fun bind(measure: BloodPressureModel, delegate: MainAdapter.Delegate) {
        with(binding) {
            upperValue.text = measure.upperValue.toString()
            downValue.text = measure.downValue.toString()
            heartRate.text = measure.heartRate.toString()
            dataTime.text = getMeasureDateTime(measure.measureTime.toLong())

            binding.root.transitionName = "blood_pressure_card_${measure.uid}"
        }

        binding.root.setOnClickListener {
            delegate.onElementClick(measure, it)
        }
    }

    private fun getMeasureDateTime(timeInMillis: Long): String {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = timeInMillis
        val measureDate = calendar.time
        return dateFormat.format(measureDate)
    }

    companion object {
        private val dateFormat: DateFormat =
            SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
    }
}
