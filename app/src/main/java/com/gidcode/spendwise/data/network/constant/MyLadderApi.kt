package com.gidcode.spendwise.data.network.constant

object MyLadderApi {
   const val BASE_URL = "https://personal-expense-tracker.myladder.africa"
   const val LOGIN = "auth/login"
   const val SIGNUP = "auth/signup"
   const val INCOME = "user/income"
   const val EXPENDITURE = "user/expenditure"
   const val USER_PROFILE = "auth/user/{userID}/profile"
}

object ApiParameters {
   const val TOKEN_TYPE = "Bearer "
   const val AUTH_HEADER = "Authorization"
   const val NO_AUTH_HEADER = "No-Authentication"
}