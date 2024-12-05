package com.gidcode.spendwise.di.usecasefactory

import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.usecase.ClearAccessTokenUseCase
import com.gidcode.spendwise.domain.usecase.GetAccessTokenUseCase
import javax.inject.Inject

class AuthUseCaseFactory @Inject constructor(
   repository: AuthRepository
) {
   val getAccessTokenUseCase = GetAccessTokenUseCase(repository)
   val clearAccessTokenUseCase = ClearAccessTokenUseCase(repository)
}