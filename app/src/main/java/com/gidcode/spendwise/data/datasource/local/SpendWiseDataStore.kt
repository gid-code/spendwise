package com.gidcode.spendwise.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpendWiseDataStore @Inject constructor(
    @ApplicationContext val context: Context
): com.gidcode.spendwise.data.datasource.local.Preferences {

   val accessToken = stringPreferencesKey("access_token")
   val tokenExpiresAt = longPreferencesKey("token_expires_at")
   private val theme = stringPreferencesKey("theme_mode")
   val userId = stringPreferencesKey("user_id")
   val userProfile = stringPreferencesKey("user_profile")
   val enableBiometric = booleanPreferencesKey("enable_biometric")

   companion object {
      private const val TAG = "SpendWiseDataStore"
   }

   override suspend fun storeAccessToken(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[accessToken] = data
      }
   }

   override fun getAccessToken(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[accessToken]
      }
   }

   override suspend fun storeTokenExpireAt(data: Long) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[tokenExpiresAt] = data
      }
   }

   override fun getTokenExpireAt(): Flow<Long?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[tokenExpiresAt]
      }
   }

   override suspend fun storeThemeMode(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[theme] = data
      }
   }

   override fun getThemeMode(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[theme]
      }
   }

   override suspend fun storeUserId(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[userId] = data
      }
   }

   override fun getUserId(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[userId]
      }
   }

   override suspend fun storeUserProfile(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[userProfile] = data
      }
   }

   override fun getUserProfile(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[userProfile]
      }
   }

   override suspend fun toggleBiometric() {
      context.spendWiseDataStore.edit { preferences ->
         preferences[enableBiometric] = !(preferences[enableBiometric] ?: false)
      }
   }

   override fun getEnableBiometric(): Flow<Boolean> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[enableBiometric] ?: false
      }
   }

   override suspend fun clearKeyData(removeKey: Preferences.Key<String>) {
      context.spendWiseDataStore.edit { preferences ->
         preferences.remove(removeKey)
      }
   }

   override suspend fun clearUserAccessInfo() {
      context.spendWiseDataStore.edit { preferences ->
         preferences.remove(accessToken)
         preferences.remove(tokenExpiresAt)
         preferences.remove(userId)
         preferences.remove(userProfile)
         preferences.remove(enableBiometric)
      }
   }

   override suspend fun clearData() {
      context.spendWiseDataStore.edit { preferences -> preferences.clear() }
   }
}

private val Context.spendWiseDataStore: DataStore<Preferences> by preferencesDataStore(name = "spendwise")