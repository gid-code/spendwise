package com.gidcode.spendwise.data.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthInterceptor @Inject constructor(
//   private val authEventHandler: AuthEventHandler
): Interceptor {
//   var authEventHandler: AuthEventHandler? = null
   override fun intercept(chain: Interceptor.Chain): Response {
      val response = chain.proceed(chain.request())

      if (response.code == 401) {
         // Trigger the navigation event
         println("Code is 401")
//         CoroutineScope(Dispatchers.Main).launch {
//            authEventHandler.onUnauthorized()
            SharedAuthState.setUnauthorized(true)
//         }
      }else {
         SharedAuthState.setUnauthorized(false)
      }

      return response
   }
}

object SharedAuthState {
   private val _isUnauthorized = MutableStateFlow(false)
   val isUnauthorized: StateFlow<Boolean> get() = _isUnauthorized

   fun setUnauthorized(value: Boolean) {
      println("in setUnauth")
      _isUnauthorized.value = value
   }
}