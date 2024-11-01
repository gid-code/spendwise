package com.gidcode.spendwise.data.network

import com.gidcode.spendwise.domain.model.Exception
import okhttp3.Interceptor
import okhttp3.Response

class NetworkStatusInterceptor(connectionManager: ConnectionManager) : Interceptor {

   val manager = connectionManager

   override fun intercept(chain: Interceptor.Chain): Response {
      return if (manager.isConnected()) {
         chain.proceed(chain.request())
      } else {
         throw Exception.NetworkUnavailableException()
      }
   }
}