package com.gidcode.spendwise.domain.repository

import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.LoginDomainModel
import com.gidcode.spendwise.util.Either
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
   suspend fun login(credential: LoginDomainModel): Either<Exception, AccessTokenDomainModel>
   suspend fun createUser(credential: CreateAccountDomainModel): Either<Exception,AccessTokenDomainModel>
   fun getAuthToken(): Flow<String?>
}