package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.SettingsRepository

class ClearUserIdUseCase(
   private val repository: SettingsRepository
) {
   suspend operator fun invoke() = repository.clearUserId()
}