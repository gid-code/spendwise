package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.SettingsRepository

class GetUserUseCase(
   private val repository: SettingsRepository
) {
   operator fun invoke() = repository.getUser()
}