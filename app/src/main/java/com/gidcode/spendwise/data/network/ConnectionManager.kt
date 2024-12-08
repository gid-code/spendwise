package com.gidcode.spendwise.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Singleton

@Singleton
class ConnectionManager (
   private val context: Context
) {
//   val context = androidContext

//   fun isConnected(): Boolean {
//      val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//      val networkInfo: NetworkInfo? = manager.activeNetworkInfo
//      return networkInfo?.isConnectedOrConnecting == true
//   }

   val isConnected: Boolean
      get() = _isConnected.get()

   private val _isConnected = AtomicBoolean(false)

   init {
      listenToNetworkChanges()
   }

   private fun listenToNetworkChanges() {
      val networkRequest = NetworkRequest.Builder()
         .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
         .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
         .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
         .build()

      val networkCallback = object : ConnectivityManager.NetworkCallback() {
         override fun onAvailable(network: Network) {
            _isConnected.set(true)
         }

         override fun onLost(network: Network) {
            _isConnected.set(false)
         }
      }

      val connectivityManager = context.getSystemService(ConnectivityManager::class.java) as ConnectivityManager
      connectivityManager.requestNetwork(networkRequest, networkCallback)
   }
}