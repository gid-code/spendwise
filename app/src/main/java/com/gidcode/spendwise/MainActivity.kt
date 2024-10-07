package com.gidcode.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gidcode.spendwise.ui.auth.Authentication
import com.gidcode.spendwise.ui.home.HomeScreen
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.navigation.ProvideNavHostController
import com.gidcode.spendwise.ui.splash.SplashScreen
import com.gidcode.spendwise.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         AppTheme {
            ProvideNavHostController {
               Box(modifier = Modifier.fillMaxSize()){
                  NavHost(Navigator.current, Destination.splash) {
                     composable(Destination.splash) { SplashScreen() }
                     composable(Destination.authentication) { Authentication() }
                     composable(Destination.home) { HomeScreen() }
                  }
               }
            }
         }
      }
   }
}
