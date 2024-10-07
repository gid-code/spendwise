package com.gidcode.spendwise.ui.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
      navController.navigate(Destination.authentication) {
         popUpTo(Destination.splash) { inclusive = true }
      }
   }
}

@Composable
fun SplashScreenContent(
   visible : Boolean
){
   Surface {
      Column(
         horizontalAlignment = Alignment.CenterHorizontally,
         verticalArrangement = Arrangement.Center,
         modifier = Modifier.fillMaxSize()
      ) {
         AnimatedTitle(visible = visible)
      }
   }
}