package com.gidcode.spendwise.data.network.interceptor

import com.gidcode.spendwise.data.datasource.local.Preferences
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.network.constant.ApiParameters.AUTH_HEADER
import com.gidcode.spendwise.data.network.constant.ApiParameters.NO_AUTH_HEADER
import com.gidcode.spendwise.data.network.constant.ApiParameters.TOKEN_TYPE
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.time.Instant
import javax.inject.Inject

class AuthenticationInterceptor(
   private val preferences: Preferences
): Interceptor {

   companion object {
      const val UNAUTHORIZED = 401
   }

   override fun intercept(chain: Interceptor.Chain): Response {
      val token = runBlocking {
         preferences.getAccessToken().first()
      }

      val tokenExpiration = runBlocking {
         preferences.getTokenExpireAt().first()
      }

      val tokenExpired = tokenExpiration?.let { Instant.ofEpochSecond(it).isBefore(Instant.now()) } ?: false
      val request = chain.request()
      if (chain.request().headers[NO_AUTH_HEADER] != null) return chain.proceed(request)

      val interceptedRequest: Request = if (tokenExpired){
         request
      }else{
         token?.let { chain.createAuthenticatedRequest(it) }!!
      }

      return chain.proceedDeletingTokenIfUnauthorized(interceptedRequest)
   }

   private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
      return request()
         .newBuilder()
         .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
         .build()
   }

   private fun Interceptor.Chain.proceedDeletingTokenIfUnauthorized(request: Request): Response {
      val response = proceed(request)

      if (response.code == UNAUTHORIZED) {
         runBlocking {
            preferences.clearUserAccessInfo()
         }
      }

      return response
   }
}
