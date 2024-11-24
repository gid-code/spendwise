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
import com.gidcode.spendwise.domain.model.Exception as Failure

class SettingsDataRepository(
   private val remoteDataSource: AuthRemoteDataSource,
   private val localDataSource: SpendWiseDataStore
): SettingsRepository {
   override suspend fun saveThemeMode(mode: ThemeMode) {
      localDataSource.storeThemeMode(mode.toString())
   }

   override fun getThemeMode(): Flow<String?> {
      return localDataSource.getThemeMode()
   }

   override suspend fun getRemoteUser(uuid: String, token: String): Either<Failure,User> {
      return try {
         val result = remoteDataSource.user(uuid, token)
         right(result.toDomain())
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun saveUser(user: User) {
      localDataSource.storeUserProfile(user.toJsonString())
   }

   override fun getUser(): Flow<String?> {
      return localDataSource.getUserProfile()
   }

   override fun getUserId(): Flow<String?> {
      return localDataSource.getUserId()
   }
}