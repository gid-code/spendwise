package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.AuthRepository

class ClearAccessTokenUseCase(
   private val repository: AuthRepository
) {
   suspend operator fun invoke() = repository.clearAccessToken()
}