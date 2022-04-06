package com.eugenics.bloodpressuremonitor.ui.common.context

import android.content.Context
import com.eugenics.bloodpressuremonitor.R

class ContextProvider(val context: Context) {
    private val strings: MutableMap<StringKeys, String> = mutableMapOf()

    init {
        strings[StringKeys.REQUIRED] = context.getString(R.string.required)
        strings[StringKeys.LOW_VALUE] = context.getString(R.string.low_value)
        strings[StringKeys.HEIGHT_VALUE] = context.getString(R.string.height_value)
        strings[StringKeys.UPPER_TEXT_FIELD] = context.getString(R.string.upper_value)
        strings[StringKeys.LOWER_TEXT_FIELD] = context.getString(R.string.lower_value)
        strings[StringKeys.HEART_RATE_TEXT_FIELD] = context.getString(R.string.heart_rate)
    }

    fun getStringFromResource(id: Int) = context.getString(id)

    fun getString(key: StringKeys) = strings[key]

    companion object {
        enum class StringKeys {
            REQUIRED,
            LOW_VALUE,
            HEIGHT_VALUE,
            UPPER_TEXT_FIELD,
            LOWER_TEXT_FIELD,
            HEART_RATE_TEXT_FIELD
        }
    }
}