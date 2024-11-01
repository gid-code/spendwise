package com.gidcode.spendwise.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionManager(androidContext: Context) {
   val context = androidContext

   fun isConnected(): Boolean {
      val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val networkInfo: NetworkInfo? = manager.activeNetworkInfo
      return networkInfo?.isConnectedOrConnecting == true
   }
}