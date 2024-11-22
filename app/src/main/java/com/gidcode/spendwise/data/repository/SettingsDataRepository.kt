package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.util.ThemeMode
import kotlinx.coroutines.flow.Flow

class SettingsDataRepository(
   private val localDataSource: SpendWiseDataStore
): SettingsRepository {
   override suspend fun saveThemeMode(mode: ThemeMode) {
      localDataSource.storeThemeMode(mode.toString())
   }

   override fun getThemeMode(): Flow<String?> {
      return localDataSource.getThemeMode()
   }
}