package com.eugenics.bloodpressuremonitor.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "bloodPressure", primaryKeys = ["uid"])
data class BloodPressureModel(
    @ColumnInfo(name = "uid")
    val uid: String,
    @ColumnInfo(name = "upperValue")
    val upperValue: Int,
    @ColumnInfo(name = "downValue")
    val downValue: Int,
    @ColumnInfo(name = "heartRate")
    val heartRate: Int,
    @ColumnInfo(name = "measureDate")
    val measureDate: String,
    @ColumnInfo(name = "measureTime")
    val measureTime: String
) : Parcelable
