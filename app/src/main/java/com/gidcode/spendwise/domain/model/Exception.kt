package com.gidcode.spendwise.domain.model

import java.io.IOException

sealed class Exception(val message: String) {
   //App
   class General(message: String):Exception(message)

   class NoNetWork(message: String = "No Internet Connection or server unreachable") : IOException(message)

   class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

   class NetworkException(message: String ): IOException(message)

   class UnAuthorizedException(message: String): Exception(message)
}
