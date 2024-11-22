package com.gidcode.spendwise.domain.repository

import com.gidcode.spendwise.util.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
   suspend fun saveThemeMode(mode: ThemeMode)
   fun getThemeMode(): Flow<String?>
}