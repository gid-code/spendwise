package com.gidcode.spendwise.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class SpendWiseDataStore(
   private val context: Context
) {
   private val accessToken = stringPreferencesKey("access_token")

   companion object {
      private const val TAG = "SpendWiseDataStore"
   }

   suspend fun storeAccessToken(data: String) {
      context.spendWiseDataStore.edit { preferences ->
         preferences[accessToken] = data
      }
   }

   suspend fun getAccessToken(): String? {
      return context.spendWiseDataStore.data.map { preferences ->
         preferences[accessToken]
      }.first()
   }
}

private val Context.spendWiseDataStore: DataStore<Preferences> by preferencesDataStore(name = "spendwise")