package com.eugenics.bloodpressuremonitor.ui.common

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.isNullOrEmpty(errorMessage: String): Boolean {
    this.error = null
    return when (this.editText?.text.toString()?.trim()) {
        null -> {
            this.error = errorMessage
            true
        }
        "" -> {
            this.error = errorMessage
            return true
        }
        "0" -> {
            this.error = errorMessage
            return true
        }
        else -> false
    }
}