package com.gidcode.spendwise.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(){
   var navigateToAuth by remember { mutableStateOf(false) }
   val navController = Navigator.current

   LaunchedEffect(Unit) {
      delay(3000)
      navigateToAuth = true
   }

   SplashScreenContent(visible = true)

   if (navigateToAuth){
      navController.navigate(Destination.Authentication.route) {
         popUpTo(Destination.Splash.route) { inclusive = true }
      }
   }
}

@Composable
fun SplashScreenContent(
   visible : Boolean
){
   Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier.fillMaxSize()
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