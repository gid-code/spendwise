package com.gidcode.spendwise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gidcode.spendwise.ui.auth.LoginScreen
import com.gidcode.spendwise.ui.auth.OnboardingScreen
import com.gidcode.spendwise.ui.auth.SignUpScreen
import com.gidcode.spendwise.ui.auth.authGraph
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
               SpendWiseApp()
            }
         }
      }
   }


}

@Composable
fun SpendWiseApp(){
   Box(modifier = Modifier.fillMaxSize()){
      NavHost(Navigator.current, Destination.Splash.route) {
         composable(Destination.Splash.route) { SplashScreen() }
         authGraph()
         composable(Destination.Home.route) { HomeScreen() }
      }
   }
}


