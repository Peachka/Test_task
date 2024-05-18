package com.example.testtaskheartrate.ui

sealed class Screen(val route: String) {
    object LoadingScreen : Screen("loading_screen")
    object OnboardingScreen1 : Screen("onboarding_screen_1")
    object OnboardingScreen2 : Screen("onboarding_screen_2")
    object OnboardingScreen3 : Screen("onboarding_screen_3")
    object HomePage : Screen("home_page")
    object MeasureScreen : Screen("measure_screen")
    object ResultScreen: Screen("result_screen")
    object IndicatorScreen : Screen("indicator_screen")
    object HistoryScreen : Screen("history_screen")

    object MedicalRecommendationsScreen : Screen("medical_recommendations_screen")
    object MedicalDetailsScreen : Screen("medical_details_screen")

    fun createResultRoute(heartRate: Int): String {
        return "result_screen/$heartRate"
    }
}