package com.gidcode.spendwise.domain.repository

import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.util.Either
import com.gidcode.spendwise.util.ThemeMode
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
   suspend fun saveThemeMode(mode: ThemeMode)
   fun getThemeMode(): Flow<String?>
   suspend fun getRemoteUser(uuid: String, token: String): Either<Exception,User>
   suspend fun saveUser(user: User)
   fun getUser(): Flow<String?>
   fun getUserId(): Flow<String?>
   fun getBiometricEnabled(): Flow<Boolean>
   suspend fun toggleBiometric()
}