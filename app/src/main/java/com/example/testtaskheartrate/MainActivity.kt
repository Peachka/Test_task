package com.example.testtaskheartrate

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.testtaskheartrate.data.PreferencesManager
import com.example.testtaskheartrate.ui.Screen
import com.example.testtaskheartrate.ui.loading_screen.LoadingScreen
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage1
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage2
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage3
import com.example.testtaskheartrate.ui.result_screen.HeartRateResultScreen

import com.example.testtaskheartrate.ui.scan_heartbeat.MeasureScreen
import com.example.testtaskheartrate.ui.theme.TestTaskHeartRateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTaskHeartRateTheme {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val navController = rememberNavController()
                val isOnboardingCompleted = PreferencesManager.isOnboardingCompleted(this)

                NavigationGraph(navController = navController, isOnboardingCompleted)
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isOnboardingCompleted: Boolean
) {
    val currentPageIndex = remember { mutableStateOf(0) }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Screen.LoadingScreen.route
    ) {
        composable(Screen.LoadingScreen.route){
            LoadingScreen(goAfterFirstLoading = {navController.navigate(Screen.OnboardingScreen1.route)},
                goAfterSecondLoading = {navController.navigate(Screen.MeasureScreen.route)},
                isOnboardingCompleted)

        }
        composable(Screen.OnboardingScreen1.route) {
            OnboardingPage1(
                onNextPage = { navController.navigate(Screen.OnboardingScreen2.route) },
                currentPageIndex = currentPageIndex.value,
                onPageIndexChanged = { currentPageIndex.value = it }
            )
        }
        composable(Screen.OnboardingScreen2.route) {
            OnboardingPage2(
                onNextPage = { navController.navigate(Screen.OnboardingScreen3.route) },
                currentPageIndex = currentPageIndex.value,
                onPageIndexChanged = { currentPageIndex.value = it }
            )
        }
        composable(Screen.OnboardingScreen3.route) {
            OnboardingPage3(
                onNextPage = { navController.navigate(Screen.MeasureScreen.route) },
                currentPageIndex = currentPageIndex.value
            )
        }

        composable(Screen.MeasureScreen.route) {
            val context = LocalContext.current
            // Set onboarding completed when MeasureScreen is reached
            PreferencesManager.setOnboardingCompleted(context, true)
            MeasureScreen(
                onResult = { heartRate ->
                    navController.navigate(Screen.ResultScreen.createResultRoute(heartRate))
                }
            )

        }
        composable(
            route = "${Screen.ResultScreen.route}/{heartRate}",
            arguments = listOf(navArgument("heartRate") { type = NavType.IntType })
        ) { backStackEntry ->
            val heartRate = backStackEntry.arguments?.getInt("heartRate")
            if (heartRate != null ) {
                HeartRateResultScreen(heartRate = heartRate,
                    goToMeasure = { navController.navigate(Screen.MeasureScreen.route) })
            }
        }
    }
}