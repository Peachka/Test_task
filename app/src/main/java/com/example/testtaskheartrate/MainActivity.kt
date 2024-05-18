package com.example.testtaskheartrate

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
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
import com.example.testtaskheartrate.data.MedicalCardsRepository
import com.example.testtaskheartrate.data.PreferencesManager
import com.example.testtaskheartrate.ui.Screen
import com.example.testtaskheartrate.ui.history_screen.HistoryScreen
import com.example.testtaskheartrate.ui.home_page.HomePage
import com.example.testtaskheartrate.ui.indicator_screen.IndicatorScreen
import com.example.testtaskheartrate.ui.loading_screen.LoadingScreen
import com.example.testtaskheartrate.ui.medical_recomendations.MedicalRecommendationsScreen
import com.example.testtaskheartrate.ui.medical_recomendations.medical_details.MedicalDetailsScreen
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage1
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage2
import com.example.testtaskheartrate.ui.onboarding_screens.OnboardingPage3
import com.example.testtaskheartrate.ui.result_screen.HeartRateResultScreen
import com.example.testtaskheartrate.ui.scan_heartbeat.MeasureScreen
import com.example.testtaskheartrate.ui.theme.TestTaskHeartRateTheme
import com.example.testtaskheartrate.ui.utils.AppBackground
import dagger.hilt.android.AndroidEntryPoint
import com.example.testtaskheartrate.ui.utils.CameraUtils

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var cameraUtils: CameraUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the CameraUtils instance
        cameraUtils = CameraUtils(this, this)

        setContent {
            TestTaskHeartRateTheme {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                val navController = rememberNavController()
                val isOnboardingCompleted = PreferencesManager.isOnboardingCompleted(this)

                NavigationGraph(
                    navController = navController,
                    isOnboardingCompleted,
                    cameraUtils = cameraUtils
                )
            }
        }
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isOnboardingCompleted: Boolean,
    cameraUtils: CameraUtils
) {
    val currentPageIndex = remember { mutableStateOf(0) }
    val medicalCardsRepository = MedicalCardsRepository()

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        navController = navController,
        startDestination = Screen.LoadingScreen.route
    ) {
        composable(Screen.LoadingScreen.route) {
            AppBackground {
                LoadingScreen(
                    goAfterFirstLoading = { navController.navigate(Screen.OnboardingScreen1.route) },
                    goAfterSecondLoading = { navController.navigate(Screen.HomePage.route) },
                    isOnboardingCompleted
                )
            }


        }
        composable(Screen.OnboardingScreen1.route) {
            AppBackground {
                BackHandler {
                    // Remove all previous screens, excluding the current screen
                    navController.popBackStack(
                        route = Screen.OnboardingScreen1.route,
                        inclusive = false
                    )
                }
                OnboardingPage1(
                    onNextPage = { navController.navigate(Screen.OnboardingScreen2.route) },
                    currentPageIndex = currentPageIndex.value,
                    onPageIndexChanged = { currentPageIndex.value = it }
                )
            }


        }
        composable(Screen.OnboardingScreen2.route) {
            AppBackground {
                OnboardingPage2(
                    onNextPage = { navController.navigate(Screen.OnboardingScreen3.route) },
                    currentPageIndex = currentPageIndex.value,
                    onPageIndexChanged = { currentPageIndex.value = it }
                )
            }


        }
        composable(Screen.OnboardingScreen3.route) {
            AppBackground {
                OnboardingPage3(
                    onNextPage = { navController.navigate(Screen.HomePage.route) },
                    currentPageIndex = currentPageIndex.value
                )
            }

        }
        composable(Screen.HomePage.route) {
            val context = LocalContext.current
            // Set onboarding completed when MeasureScreen is reached
            PreferencesManager.setOnboardingCompleted(context, true)

            BackHandler {
                // Remove all previous screens, excluding the current screen
//                navController.popBackStack(route = Screen.HomePage.route, inclusive = false)
            }
            HomePage(
                startMeasure = { navController.navigate(Screen.MeasureScreen.route) },
                goToIndicatorScreen = { navController.navigate(Screen.IndicatorScreen.route) },
                goToHistoryScreen = { navController.navigate(Screen.HistoryScreen.route) },
                goToMedicalRecomendations = { navController.navigate(Screen.MedicalRecommendationsScreen.route) },
                cameraUtils = cameraUtils
            )

        }
        composable(Screen.HistoryScreen.route) {

            HistoryScreen(goBackToHomeScreen = { navController.navigate(Screen.HomePage.route) })

        }

        composable(Screen.IndicatorScreen.route) {

            IndicatorScreen(goToHomePage = { navController.navigate(Screen.HomePage.route) })

        }

        composable(Screen.MeasureScreen.route) {
            AppBackground {
                MeasureScreen(
                    onResult = { heartRate ->
                        navController.navigate(Screen.ResultScreen.createResultRoute(heartRate))
                    },
                    onBack = { navController.navigate(Screen.HomePage.route) }
                )
            }

        }

        composable(
            route = "${Screen.ResultScreen.route}/{heartRate}",
            arguments = listOf(navArgument("heartRate") { type = NavType.IntType })
        ) { backStackEntry ->
            val heartRate = backStackEntry.arguments?.getInt("heartRate")
            if (heartRate != null) {
                AppBackground {
                    BackHandler {
                        // Remove all previous screens, excluding the current screen
                        navController.popBackStack(route = Screen.HomePage.route, inclusive = false)
                    }
                    HeartRateResultScreen(heartRate = heartRate,
                        goToHistoryScreen = { navController.navigate(Screen.HistoryScreen.route) },
                        goToHomeScreen = { navController.navigate(Screen.HomePage.route) })
                }


            }
        }

        composable(Screen.MedicalRecommendationsScreen.route) {

            MedicalRecommendationsScreen(
                onBackScreen = { navController.navigate(Screen.HomePage.route) },
                medicalCardsRepository = medicalCardsRepository,
                goToDetailsScreen = { id -> navController.navigate("${Screen.MedicalDetailsScreen.route}/$id") }
            )


        }

        composable(
            "${Screen.MedicalDetailsScreen.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id")
            id?.let {

                MedicalDetailsScreen(
                    medicalCardsRepository = medicalCardsRepository,
                    id = it,
                    onBackScreen = {navController.navigate(Screen.MedicalRecommendationsScreen.route)}
                )

            }
        }
    }
}