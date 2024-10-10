package com.gidcode.spendwise.ui.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gidcode.spendwise.ui.navigation.Destination

fun NavGraphBuilder.authGraph(){
      navigation(
         route = Destination.Authentication.route,
         startDestination = Destination.OnBoarding.route
      ) {
         composable(Destination.OnBoarding.route) { OnboardingScreen() }
         composable(Destination.SignUp.route) { SignUpScreen() }
         composable(Destination.Login.route) { LoginScreen() }
      }
}