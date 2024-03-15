package com.personal.weathering.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.personal.weathering.R
import com.personal.weathering.WeatheringApp
import com.personal.weathering.presentation.navigation.RootNavigationGraph
import com.personal.weathering.presentation.ui.components.CustomDialog
import com.personal.weathering.presentation.ui.theme.WeatheringTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        viewModelFactory {
            MainViewModel(
                weatherRepository = WeatheringApp.appModule.weatherRepository,
                aqRepository = WeatheringApp.appModule.aqRepository,
                locationClient = WeatheringApp.appModule.locationClient,
                localRepository = WeatheringApp.appModule.localRepository
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.holdSplash
        }

        setContent {
            WeatheringTheme(
                darkTheme = mainViewModel.preferencesState.collectAsState().value.isDark
            ) {
                val permissionsResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = {
                        mainViewModel.uiEvent(UiEvent.SetUseLocation)
                    }
                )
                Surface {
                    RootNavigationGraph(
                        navController = rememberNavController(),
                        mainViewModel = mainViewModel,
                        requestPermissions = { permissionsResultLauncher.launch(permissionsToRequest()) }
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

            LaunchedEffect(mainViewModel.locationError) {
                mainViewModel.locationError.collectLatest { message ->
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun permissionsToRequest(): Array<String> {
        val permissionsArray = arrayListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        return permissionsArray.toTypedArray()
    }

    fun hasPermissions(permissions: Array<String> = permissionsToRequest()): Boolean {
        return permissions.any { permission ->
            ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    fun shouldShowRationale(): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) || ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
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

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
