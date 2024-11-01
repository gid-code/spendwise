package com.gidcode.spendwise.domain.model

import java.io.IOException

sealed class Exception(val message: String) {
   //App
   class General(message: String):Exception(message)

   class NoNetWork(message: String):Exception(message)

   class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

   class NetworkException(message: String): Exception(message)

   class UnAuthorizedException(message: String): Exception(message)
}
