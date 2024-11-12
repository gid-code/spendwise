package com.gidcode.spendwise.data.network

import com.gidcode.spendwise.domain.model.Exception
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkStatusInterceptor(connectionManager: ConnectionManager) : Interceptor {

   val manager = connectionManager

   override fun intercept(chain: Interceptor.Chain): Response {
      return if (manager.isConnected()) {
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