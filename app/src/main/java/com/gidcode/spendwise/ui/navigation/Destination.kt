package com.gidcode.spendwise.ui.navigation

sealed class Destination(val route: String) {
   data object Splash : Destination("splash")
   data object Home : Destination("home")
   data object OnBoarding : Destination("onboarding")
   data object SignUp : Destination("signup")
   data object Login : Destination("login")
   data object Authentication : Destination("auth")
   data object Dashboard : Destination("dashboard")
   data object Expenses : Destination("expenses")
   data object Profile : Destination("profile")
}