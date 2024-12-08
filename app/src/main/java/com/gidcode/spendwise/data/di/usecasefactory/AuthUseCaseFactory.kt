package com.gidcode.spendwise.data.di.usecasefactory

import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.usecase.ClearAccessTokenUseCase
import com.gidcode.spendwise.domain.usecase.GetAccessTokenUseCase
import com.gidcode.spendwise.domain.usecase.LogoutUseCase
import javax.inject.Inject

class AuthUseCaseFactory @Inject constructor(
   repository: AuthRepository
) {
   val getAccessTokenUseCase = GetAccessTokenUseCase(repository)
   val clearAccessTokenUseCase = ClearAccessTokenUseCase(repository)
   val logoutUseCase = LogoutUseCase(repository)
}