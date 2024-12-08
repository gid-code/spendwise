package com.gidcode.spendwise.data.network.interceptor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
   override fun intercept(chain: Interceptor.Chain): Response {
      val response = chain.proceed(chain.request())

      if (response.code == 401) {
         // Trigger the navigation event
         println("Code is 401")
         SharedAuthState.setUnauthorized(true)
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