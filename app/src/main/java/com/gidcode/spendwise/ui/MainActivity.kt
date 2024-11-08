package com.gidcode.spendwise.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gidcode.spendwise.data.network.AuthEventHandler
import com.gidcode.spendwise.ui.auth.authGraph
import com.gidcode.spendwise.ui.common.ProvideMultiViewModel
import com.gidcode.spendwise.ui.home.MainScreen
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.navigation.ProvideNavHostController
import com.gidcode.spendwise.ui.splash.SplashScreen
import com.gidcode.spendwise.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         AppTheme {
            ProvideMultiViewModel {
               ProvideNavHostController {
                  SpendWiseApp()
               }
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
         composable(Destination.Main.route) { MainScreen() }
      }
   }
}


