package com.gidcode.spendwise.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gidcode.spendwise.ui.auth.SignUpScreenContent
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.BottomNavItems
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun HomeScreen() {
   HomeScreenContent()

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(){
//   val navController = Navigator.current
   val navController = rememberNavController()

   val items = mutableListOf(
      BottomNavItems.DashboardItem,
      BottomNavItems.ExpensesItem,
      BottomNavItems.Profile
   )

   Scaffold (
      bottomBar = {
         NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

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
                  label = { Text(text = item.title) }
               )
            }
         }
      }
   ){
      NavHost(
         navController = navController,
         route = Destination.Home.route,
         startDestination = Destination.Dashboard.route
      ) {
         composable(route = Destination.Dashboard.route){
            DashboardScreen()
         }

         composable(route = Destination.Expenses.route){
            ExpensesScreen()
         }

         composable(route = Destination.Profile.route){
            ProfileScreen()
         }
      }
   }
}

@Composable
fun DashboardScreen() {
   Box(modifier = Modifier.fillMaxSize()) {
      Text(text = "Dashboard")
   }
}

@Composable
fun ExpensesScreen() {
   Box(modifier = Modifier.fillMaxSize()) {
      Text(text = "Expenses")
   }
}

@Composable
fun ProfileScreen() {
   Box(modifier = Modifier.fillMaxSize()) {
      Text(text = "Profile")
   }
}

@Preview(name = "Home")
@Composable
fun HomeScreenPreview() {
   PreviewContent {
      HomeScreenContent()
   }
}

@Preview(name = "Home Dark")
@Composable
fun HomeScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      HomeScreenContent()
   }
}
