package com.gidcode.spendwise.ui.home.settings

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.gidcode.spendwise.ui.navigation.Destination

fun NavGraphBuilder.settingsGraph(){
   navigation(
      route = Destination.Settings.route,
      startDestination = Destination.SettingsScreen.route,
      enterTransition = {
         slideInHorizontally(initialOffsetX = {40})
      },
      exitTransition = {
         slideOutHorizontally()
      }
   ) {
      composable(Destination.SettingsScreen.route) { SettingsScreen() }
      composable(Destination.AccountInfo.route) { AccountInfoScreen() }
      composable(Destination.Appearance.route) { AppearanceScreen() }
      composable(Destination.Notifications.route) { NotificationScreen() }
      composable(Destination.Privacy.route) { PrivacyScreen() }
      composable(Destination.Security.route) { SecurityScreen() }
      dialog(Destination.ConfirmLogout.route) { ConfirmLogoutDialog() }
   }
}

@Composable
fun PrivacyScreen() {
}
