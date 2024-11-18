package com.gidcode.spendwise.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.BottomNavItems
import com.gidcode.spendwise.ui.navigation.Destination

@Composable
fun MainScreen() {
   MainScreenContent()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreenContent(){
//   val navController = Navigator.current
   val navController = rememberNavController()

   val items = mutableListOf(
      BottomNavItems.HomeItem,
      BottomNavItems.ExpensesItem,
      BottomNavItems.Settings
   )

   Scaffold (
      floatingActionButton = {
         FloatingActionButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon" )
         }
      },
      bottomBar = {
         NavigationBar(
//            containerColor = MaterialTheme.colorScheme.surface
         ) {
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
   ){
      NavHost(
         navController = navController,
         route = Destination.Main.route,
         startDestination = Destination.Home.route
      ) {
         composable(route = Destination.Home.route){
            HomeScreen()
         }
         composable(route = Destination.Expenses.route){
            ExpensesScreen()
         }

         composable(route = Destination.Settings.route){
            ProfileScreen()
         }
      }
   }
}

@Composable
fun ProfileScreen() {
   Box(modifier = Modifier.fillMaxSize()) {
      Text(text = "Profile")
   }
}

@Preview(name = "Main")
@Composable
fun MainScreenPreview() {
   PreviewContent {
      MainScreenContent()
   }
}

@Preview(name = "Main Dark")
@Composable
fun MainScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      MainScreenContent()
   }
}
