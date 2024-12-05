package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.SettingsRepository

class GetBiometricEnabledUseCase(
   private val repository: SettingsRepository
) {
   operator fun invoke() = repository.getBiometricEnabled()
}