package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.AuthRemoteDataSource
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.util.Either
import com.gidcode.spendwise.util.ThemeMode
import com.gidcode.spendwise.util.left
import com.gidcode.spendwise.util.right
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.gidcode.spendwise.domain.model.Exception as Failure

class SettingsDataRepository @Inject constructor(
   private val remoteDataSource: AuthRemoteDataSource,
   private val localDataStore: SpendWiseDataStore
): SettingsRepository {
   override suspend fun saveThemeMode(mode: ThemeMode) {
      localDataStore.storeThemeMode(mode.toString())
   }

   override fun getThemeMode(): Flow<String?> {
      return localDataStore.getThemeMode()
   }

   override suspend fun getRemoteUser(uuid: String): Either<Failure,User> {
      return try {
         val result = remoteDataSource.user(uuid)
         right(result.toDomain())
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun saveUser(user: User) {
      localDataStore.storeUserProfile(user.toJsonString())
   }

   override fun getUser(): Flow<String?> {
      return localDataStore.getUserProfile()
   }

   override fun getUserId(): Flow<String?> {
      return localDataStore.getUserId()
   }

   override fun getBiometricEnabled(): Flow<Boolean> {
      return localDataStore.getEnableBiometric()
   }

   override suspend fun toggleBiometric() {
      localDataStore.toggleBiometric()
   }

   override suspend fun clearUserId() {
      localDataStore.clearKeyData(localDataStore.userId)
   }

   override suspend fun clearUser() {
      localDataStore.clearKeyData(localDataStore.userProfile)
   }
}