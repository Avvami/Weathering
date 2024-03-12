package com.personal.weathering.presentation

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.personal.weathering.R
import com.personal.weathering.WeatheringApp
import com.personal.weathering.presentation.navigation.RootNavigationGraph
import com.personal.weathering.presentation.ui.components.CustomDialog
import com.personal.weathering.presentation.ui.theme.WeatheringTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory {
            MainViewModel(
                weatherRepository = WeatheringApp.appModule.weatherRepository,
                aqRepository = WeatheringApp.appModule.aqRepository,
                locationTracker = WeatheringApp.appModule.locationTracker,
                localRepository = WeatheringApp.appModule.localRepository
            )
        }
    }
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.holdSplash
        }

        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {}
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            WeatheringTheme(
                darkTheme = mainViewModel.preferencesState.collectAsState().value.isDark
            ) {
                Surface {
                    RootNavigationGraph(
                        navController = rememberNavController(),
                        mainViewModel = mainViewModel
                    )
                    CustomDialog(
                        iconRes = mainViewModel.messageDialogState.iconRes,
                        titleRes = mainViewModel.messageDialogState.titleRes,
                        messageRes = mainViewModel.messageDialogState.messageRes,
                        messageString = mainViewModel.messageDialogState.messageString,
                        onDismissRequest = { mainViewModel.uiEvent(UiEvent.CloseMessageDialog) },
                        dismissTextRes = mainViewModel.messageDialogState.dismissTextRes,
                        onDismiss = mainViewModel.messageDialogState.onDismiss,
                        confirmTextRes = mainViewModel.messageDialogState.confirmTextRes,
                        onConfirm = mainViewModel.messageDialogState.onConfirm,
                        showDialog = mainViewModel.messageDialogState.isShown
                    )
                }
            }
        }
    }

    fun openCustomWebTab(url: String) {
        val chromeIntent = CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(
                CustomTabColorSchemeParams.Builder()
                    .setToolbarColor(ContextCompat.getColor(this, R.color.surfaceLight))
                    .build())
            .setColorSchemeParams(CustomTabsIntent.COLOR_SCHEME_DARK, CustomTabColorSchemeParams.Builder()
                .setToolbarColor(ContextCompat.getColor(this, R.color.surfaceDark))
                .build())
            .setUrlBarHidingEnabled(true)
            .setShowTitle(true)
            .build()
        chromeIntent.launchUrl(this, Uri.parse(url))
    }
}