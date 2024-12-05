package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.SettingsRepository

class ClearUserUseCase(
   private val repository: SettingsRepository
) {
   suspend operator fun invoke() = repository.clearUser()
}