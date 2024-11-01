package com.gidcode.spendwise.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(
   val title: String,
   val route: String,
   val icon: ImageVector
){
   data object HomeItem : BottomNavItems(
      title = "Home",
      route = Destination.Home.route,
      icon =  Icons.Filled.Home
   )

   data object ExpensesItem : BottomNavItems(
      title = "Expenses",
      route = Destination.Expenses.route,
      icon =  Icons.Filled.Money
   )

   data object Settings : BottomNavItems(
      title = "Settings",
      route = Destination.Settings.route,
      icon = Icons.Default.Settings
   )
}