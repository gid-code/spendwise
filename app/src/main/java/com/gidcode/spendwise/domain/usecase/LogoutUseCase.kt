package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.AuthRepository

class LogoutUseCase(
   private val repository: AuthRepository
) {
   suspend operator fun invoke() = repository.logout()
}