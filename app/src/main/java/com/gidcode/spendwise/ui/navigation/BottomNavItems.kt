package com.gidcode.spendwise.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItems(
   val title: String,
   val route: String,
   val icon: ImageVector
){
   data object DashboardItem : BottomNavItems(
      title = "Dashboard",
      route = Destination.Dashboard.route,
      icon =  Icons.Filled.Dashboard
   )

   data object ExpensesItem : BottomNavItems(
      title = "Expenses",
      route = Destination.Expenses.route,
      icon =  Icons.Filled.Dashboard
   )

   data object Profile : BottomNavItems(
      title = "Profile",
      route = Destination.Profile.route,
      icon = Icons.Filled.Person
   )
}