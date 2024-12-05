package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.AuthRemoteDataSource
import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.domain.model.Exception as Failure
import com.gidcode.spendwise.domain.model.LoginDomainModel
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.util.Either
import com.gidcode.spendwise.util.left
import com.gidcode.spendwise.util.right
import kotlinx.coroutines.flow.Flow

class AuthDataRepository(
   private val remoteDataSource: AuthRemoteDataSource,
   private val localDataSource: SpendWiseDataStore
): AuthRepository {
   override suspend fun login(credential: LoginDomainModel): Either<Failure, AccessTokenDomainModel> {
      return try {
         val result = remoteDataSource.login(credential.toApi()).toAccessTokenModel()
         localDataSource.storeAccessToken(result.token)
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun createUser(credential: CreateAccountDomainModel): Either<Failure, AccessTokenDomainModel> {
      return try {
         remoteDataSource.createUser(credential.toApi())
         login(credential.toLoginDomain())
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override fun getAuthToken(): Flow<String?> {
      return localDataSource.getAccessToken()
   }

   override suspend fun clearAccessToken() {
      localDataSource.clearKeyData(localDataSource.accessToken)
   }

}