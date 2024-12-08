package com.gidcode.spendwise.data.network.interceptor

import com.gidcode.spendwise.data.network.ConnectionManager
import com.gidcode.spendwise.domain.model.Exception
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkStatusInterceptor(
   private val connectionManager: ConnectionManager
) : Interceptor {

   override fun intercept(chain: Interceptor.Chain): Response {
      return if (connectionManager.isConnected) {
//         chain.proceed(chain.request())
         try {
            chain.proceed(chain.request())
         }catch (e: UnknownHostException){
            throw Exception.NoNetWork()
         }catch (e: SocketTimeoutException){
            throw Exception.NoNetWork()
         }
         catch (e: IOException){
            throw e.message?.let { Exception.NetworkException(it) }!!
         }
      } else {
         throw Exception.NetworkUnavailableException()
      }
   }
}