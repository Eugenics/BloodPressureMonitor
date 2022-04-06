package com.eugenics.bloodpressuremonitor.ui.navigation

sealed class Screen(val route: String) {
    object Main : Screen(route = "main_screen")
    object Detail : Screen(route = "detail_screen/{measureId}") {
        fun passMeasureId(measureId: String) = "detail_screen/$measureId"
    }

    object Settings : Screen(route = "settings_screen")
}
