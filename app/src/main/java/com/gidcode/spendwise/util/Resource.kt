package com.gidcode.spendwise.util

import com.gidcode.spendwise.domain.model.Exception

sealed class Resource<out T> {
   data class Success<out T>(val data: T) : Resource<T>()
   data class Error(val failure: Exception) : Resource<Nothing>()
   object Loading : Resource<Nothing>()
}