package com.gidcode.spendwise.ui.auth

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.gidcode.spendwise.ui.navigation.Destination

fun NavGraphBuilder.authGraph(){
      navigation(
         route = Destination.Authentication.route,
         startDestination = Destination.OnBoarding.route,
         enterTransition = {
            slideInHorizontally(initialOffsetX = {+10})
         },
         exitTransition = {
            slideOutHorizontally()
         }
      ) {
         composable(Destination.OnBoarding.route) { OnboardingScreen() }
         composable(Destination.SignUp.route) { SignUpScreen() }
         composable(Destination.Login.route) { LoginScreen() }
      }
}