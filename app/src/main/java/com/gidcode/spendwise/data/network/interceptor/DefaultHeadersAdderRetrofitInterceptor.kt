package com.gidcode.spendwise.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class DefaultHeadersAdderRetrofitInterceptor : Interceptor {

   @Throws(IOException::class)
   override fun intercept(chain: Interceptor.Chain): Response {
      val newRequest = chain.request()
         .newBuilder()
         .addHeader("Content-Type", "application/json")
         .addHeader("Accept", "application/json")
         .build()
      return chain.proceed(newRequest)
   }
}