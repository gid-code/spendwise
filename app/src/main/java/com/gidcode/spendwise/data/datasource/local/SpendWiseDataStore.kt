package com.gidcode.spendwise.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SpendWiseDataStore(
   private val context: Context
) {
   val accessToken = stringPreferencesKey("access_token")
   private val theme = stringPreferencesKey("theme_mode")
   val userId = stringPreferencesKey("user_id")
   val userProfile = stringPreferencesKey("user_profile")

   companion object {
      private const val TAG = "SpendWiseDataStore"
   }

   suspend fun storeAccessToken(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[accessToken] = data
      }
   }

   fun getAccessToken(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[accessToken]
      }
   }

   suspend fun storeThemeMode(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[theme] = data
      }
   }

   fun getThemeMode(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[theme]
      }
   }

   suspend fun storeUserId(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[userId] = data
      }
   }

   fun getUserId(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[userId]
      }
   }

   suspend fun storeUserProfile(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[userProfile] = data
      }
   }

   fun getUserProfile(): Flow<String?> {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[userProfile]
      }
   }

   suspend fun clearKeyData(removeKey: Preferences.Key<String>): Preferences {
      return context.spendWiseDataStore.edit { preferences ->
         preferences.remove(removeKey)
      }
   }
}

private val Context.spendWiseDataStore: DataStore<Preferences> by preferencesDataStore(name = "spendwise")