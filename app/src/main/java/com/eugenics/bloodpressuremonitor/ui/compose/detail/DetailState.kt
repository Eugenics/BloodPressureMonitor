package com.eugenics.bloodpressuremonitor.ui.compose.detail

sealed class DetailState(val message: String) {
    object Opened : DetailState(message = "opened")
    object Edited : DetailState(message = "edited")
    object Closing : DetailState(message = "closing")
    object Closed : DetailState(message = "closed")
    object Error : DetailState(message = "error")
    object Delete : DetailState(message = "delete")
}
