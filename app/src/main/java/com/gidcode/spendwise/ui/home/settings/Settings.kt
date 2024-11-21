package com.gidcode.spendwise.ui.home.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gidcode.spendwise.ui.navigation.Destination

fun NavGraphBuilder.settingsGraph(){
   navigation(
      route = Destination.Settings.route,
      startDestination = Destination.SettingsScreen.route
   ) {
      composable(Destination.SettingsScreen.route) { SettingsScreen() }
      composable(Destination.AccountInfo.route) { AccountInfoScreen() }
      composable(Destination.Appearance.route) {  }
   }
}