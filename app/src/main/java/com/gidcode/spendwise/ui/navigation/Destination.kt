package com.gidcode.spendwise.ui.navigation

sealed class Destination(val route: String) {
   data object Splash : Destination("splash")
   data object Home : Destination("home")
   data object OnBoarding : Destination("onboarding")
   data object SignUp : Destination("signup")
   data object Login : Destination("login")
   data object Authentication : Destination("auth")
   data object Main : Destination("main")
   data object Expenses : Destination("expenses")
   data object Profile : Destination("profile")
   data object Settings: Destination("settings")
   data object AddIncome: Destination("add-income")
   data object AddExpenses: Destination("add-expenses")
}