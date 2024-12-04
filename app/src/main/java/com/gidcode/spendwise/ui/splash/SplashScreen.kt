package com.gidcode.spendwise.ui.splash

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.BiometricPromptManager
import com.gidcode.spendwise.ui.common.ErrorViewWithoutButton
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.*
import com.gidcode.spendwise.util.getActivity
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(){
   var navigateToAuth by remember { mutableStateOf(false) }
   val authViewModel = ViewModelProvider.authToken
   val uiState by authViewModel.uiState.collectAsState()
   val navController = Navigator.current
   val context = LocalContext.current

   val promptManager by lazy {
      BiometricPromptManager(context as AppCompatActivity)
   }

   LaunchedEffect(Unit) {
      delay(3000)
      navigateToAuth = true
   }

   LaunchedEffect(key1 = uiState.hasAuthToken, key2 = navigateToAuth, key3 = uiState.isBiometricEnabled) {
      if (navigateToAuth) {
         if (uiState.hasAuthToken){
            if (uiState.isBiometricEnabled){
               promptManager.showBiometricPrompt(
                  title = "Complete Biometric Authentication",
                  description = "Complete authentication to continue"
               )
            }else {
               navController.navigate(Destination.Main.route) {
                  popUpTo(Destination.Splash.route) { inclusive = true }
               }
            }
         }else {
            navController.navigate(Destination.Authentication.route) {
               popUpTo(Destination.Splash.route) { inclusive = true }
            }
         }
      }

   }

   SplashScreenContent(visible = true)

}

@Composable
fun SplashScreenContent(
   visible : Boolean
){
   val context = LocalContext.current
   val navController = Navigator.current
   val promptManager by lazy {
      BiometricPromptManager(context as AppCompatActivity)
   }
   var showError by remember { mutableStateOf(false) }
   var errorMsg by remember { mutableStateOf("") }

   val promptResult by promptManager.promptResults.collectAsState(initial = null)

   val enrollLauncher = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.StartActivityForResult(),
      onResult = {
         println("Activity result: $it")
      }
   )

   LaunchedEffect(promptResult) {
      if (promptResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet){
         if (Build.VERSION.SDK_INT >= 30 ){
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
               putExtra(
                  Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                  BIOMETRIC_STRONG or DEVICE_CREDENTIAL
               )
            }

            enrollLauncher.launch(enrollIntent)
         }
      }

      when (promptResult) {
         is BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
            navController.navigate(Destination.Main.route) {
               popUpTo(Destination.Splash.route) { inclusive = true }
            }
         }
         is BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
            showError = true
            errorMsg = "Authentication not set"
         }
         is BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
            showError = true
            errorMsg = "Authentication failed"
         }
         is BiometricPromptManager.BiometricResult.AuthenticationError -> {
            showError = true
            errorMsg = (promptResult as BiometricPromptManager.BiometricResult.AuthenticationError).error
         }
         is BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
            showError = true
            errorMsg = "Feature unavailable"
         }
         is BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
            showError = true
            errorMsg = "Feature unavailable"
         }
         else -> {
            showError = true
            errorMsg = "Unknown error"
         }
      }
   }
   Box {
      Column(
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center,
         modifier = Modifier
            .fillMaxSize()
            .background(
               brush = Brush.linearGradient(
                  colors = listOf(
                     Color(0xFF63B5AF),
                     Color(0xFF438883)
                  )
               )
            )
      ) {
         AnimatedTitle(visible = visible)
      }

      Box(
         modifier = Modifier
            .align(Alignment.TopCenter)
            .padding(top = 100.dp)
      ) {
         ErrorViewWithoutButton(text = errorMsg, visible = showError) {
            showError = false
         }
      }
   }
}

@Preview(name = "Splash")
@Composable
fun SplashScreenPreview() {
   PreviewContent {
      SplashScreenContent(visible = true)
   }
}

@Preview(name = "Splash (Dark)")
@Composable
fun SplashScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      SplashScreenContent(visible = true)
   }
}