package com.gidcode.spendwise.data.datasource.local

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface Preferences {
   suspend fun storeAccessToken(data: String)
   fun getAccessToken(): Flow<String?>
   suspend fun storeTokenExpireAt(data: Long)
   fun getTokenExpireAt(): Flow<Long?>
   suspend fun storeThemeMode(data: String)
   fun getThemeMode(): Flow<String?>
   suspend fun storeUserId(data: String)
   fun getUserId(): Flow<String?>
   suspend fun storeUserProfile(data: String)
   fun getUserProfile(): Flow<String?>
   suspend fun toggleBiometric()
   fun getEnableBiometric(): Flow<Boolean>
   suspend fun clearKeyData(removeKey: Preferences.Key<String>)
   suspend fun clearUserAccessInfo()
   suspend fun clearData()
}