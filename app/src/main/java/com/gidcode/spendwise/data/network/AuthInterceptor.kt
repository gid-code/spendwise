package com.gidcode.spendwise.data.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthInterceptor (
//   private val authEventHandler: AuthEventHandler
): Interceptor {
   var authEventHandler: AuthEventHandler? = null
   override fun intercept(chain: Interceptor.Chain): Response {
      val response = chain.proceed(chain.request())

      if (response.code == 401) {
         // Trigger the navigation event
         CoroutineScope(Dispatchers.Main).launch {
            authEventHandler?.onUnauthorized()
         }
      }

      return response
   }
}