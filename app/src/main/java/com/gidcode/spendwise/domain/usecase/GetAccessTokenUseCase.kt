package com.gidcode.spendwise.domain.usecase

import com.gidcode.spendwise.domain.repository.AuthRepository

class GetAccessTokenUseCase(
   private val repository: AuthRepository
) {
   operator fun invoke() = repository.getAuthToken()
}