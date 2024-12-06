package com.gidcode.spendwise.data.di.usecasefactory

import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.domain.usecase.ClearUserIdUseCase
import com.gidcode.spendwise.domain.usecase.ClearUserUseCase
import com.gidcode.spendwise.domain.usecase.GetBiometricEnabledUseCase
import com.gidcode.spendwise.domain.usecase.GetRemoteUserUseCase
import com.gidcode.spendwise.domain.usecase.GetUserIdUseCase
import com.gidcode.spendwise.domain.usecase.GetUserUseCase
import com.gidcode.spendwise.domain.usecase.StoreUserUseCase
import javax.inject.Inject

class UserUseCaseFactory @Inject constructor(
   repository: SettingsRepository
) {
   val getUserUseCase = GetUserUseCase(repository)
   val getUserIdUseCase = GetUserIdUseCase(repository)
   val getRemoteUserUseCase = GetRemoteUserUseCase(repository)
   val storeUserUseCase = StoreUserUseCase(repository)
   val clearUserUseCase = ClearUserUseCase(repository)
   val clearUserIdUseCase = ClearUserIdUseCase(repository)
   val getBiometricEnabledUseCase = GetBiometricEnabledUseCase(repository)
}