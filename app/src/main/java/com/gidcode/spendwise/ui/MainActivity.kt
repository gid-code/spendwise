package com.gidcode.spendwise.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gidcode.spendwise.ui.auth.authGraph
import com.gidcode.spendwise.ui.common.ProvideMultiViewModel
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.home.mainScreenGraph
import com.gidcode.spendwise.ui.navigation.BottomNavItems
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.navigation.ProvideNavHostController
import com.gidcode.spendwise.ui.splash.SplashScreen
import com.gidcode.spendwise.ui.theme.AppTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      enableEdgeToEdge()
      setContent {
         ProvideWindowInsets {
            ProvideMultiViewModel {
               val settingsViewModel = ViewModelProvider.settingsViewModel
               val themeMode by settingsViewModel.themeMode.collectAsState()
               AppTheme(themeMode) {
                  ProvideNavHostController {
                     SpendWiseApp()
                  }
               }
            }
         }
      }
   }


}

@Composable
fun SpendWiseApp(){
   val navController = Navigator.current
   var showBottomNav by rememberSaveable { mutableStateOf(true) }
   val navBackStackEntry by navController.currentBackStackEntryAsState()
   val currentDestination = navBackStackEntry?.destination

   val needBottomBar = listOf(
      Destination.Home.route,
      Destination.Expenses.route,
      Destination.SettingsScreen.route
   )

   val items = listOf(
      BottomNavItems.HomeItem,
      BottomNavItems.ExpensesItem,
      BottomNavItems.Settings
   )

   Scaffold (
      floatingActionButton = {
         if (navController.currentDestination?.route == Destination.Expenses.route) {
            FloatingActionButton(onClick = { navController.navigate(Destination.AddExpenses.route) }) {
               Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
            }
         }
      },
      bottomBar = {
         if (showBottomNav) {
            NavigationBar {
               items.forEach { item ->
                  NavigationBarItem(
                     selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                     onClick = {
                        navController.navigate(item.route) {
                           // Pop up to the start destination of the graph to
                           // avoid building up a large stack of destinations
                           // on the back stack as users select items
                           popUpTo(navController.graph.findStartDestination().id) {
                              saveState = true
                           }
                           // Avoid multiple copies of the same destination when
                           // reselecting the same item
                           launchSingleTop = true
                           // Restore state when reselecting a previously selected item
                           restoreState = true
                        }
                     },
                     icon = {
                        Icon(imageVector = item.icon, contentDescription = item.title)
                     },
                     label = {
                        Text(text = item.title)
                     },
                     colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                     )
                  )
               }
            }
         }
      }
   ) { padding ->
      Box(modifier = Modifier.padding(bottom = padding.calculateBottomPadding())) {
         NavHost(navController, Destination.Splash.route) {
            composable(Destination.Splash.route) { SplashScreen() }
            authGraph()
            mainScreenGraph()
         }
      }
   }

   showBottomNav = needBottomBar.contains(currentDestination?.route)
}


