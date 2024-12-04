package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.domain.repository.SettingsRepository

class StoreUserUseCase(
   private val repository: SettingsRepository
) {
   suspend operator fun invoke(
      user: User
   ) = repository.saveUser(user)
}