package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.SettingsRepository

class GetUserIdUseCase(
   private val repository: SettingsRepository
) {
   operator fun invoke() = repository.getUserId()
}