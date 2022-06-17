package com.eugenics.bloodpressuremonitor.ui.compose.main

sealed class MainState(val message: String) {
    object Loaded : MainState("loaded")
    object Load : MainState("load")
    object Refresh : MainState("refresh")
}
