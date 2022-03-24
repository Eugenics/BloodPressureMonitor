package com.eugenics.bloodpressuremonitor.ui.fragments.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eugenics.bloodpressuremonitor.R
import com.eugenics.bloodpressuremonitor.domain.models.BloodPressureModel

class MainAdapter(private val delegate: Delegate) :
    ListAdapter<BloodPressureModel, MainViewHolder>(differ) {

    interface Delegate {
        fun onElementClick(measure: BloodPressureModel?, view: View)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder =
        MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.blood_pressure_card, parent, false)
        )

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position), delegate)
    }

    companion object {
        private val differ: DiffUtil.ItemCallback<BloodPressureModel> =
            object : DiffUtil.ItemCallback<BloodPressureModel>() {
                override fun areItemsTheSame(
                    oldItem: BloodPressureModel,
                    newItem: BloodPressureModel
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: BloodPressureModel,
                    newItem: BloodPressureModel
                ): Boolean =
                    oldItem.uid == newItem.uid

            }
    }
}