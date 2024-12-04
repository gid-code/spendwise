package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.util.Either

class GetRemoteUserUseCase(
   private val repository: SettingsRepository
) {
   suspend operator fun invoke(
      uuid: String,
      token: String
   ): Either<Exception,User> = repository.getRemoteUser(uuid,token)
}