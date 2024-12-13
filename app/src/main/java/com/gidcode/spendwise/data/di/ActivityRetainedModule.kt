package com.gidcode.spendwise.data.di

import com.gidcode.spendwise.data.repository.AuthDataRepository
import com.gidcode.spendwise.data.repository.HomeDataRepository
import com.gidcode.spendwise.data.repository.SettingsDataRepository
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedModule {

   @Binds
   @ActivityRetainedScoped
   abstract fun bindAuthRepository(repository: AuthDataRepository): AuthRepository

   @Binds
   @ActivityRetainedScoped
   abstract fun bindHomeRepository(repository: HomeDataRepository): HomeRepository

   @Binds
   @ActivityRetainedScoped
   abstract fun bindSettingsRepository(repository: SettingsDataRepository): SettingsRepository

//   @Binds
//   @ActivityRetainedScoped
//   fun bindUserUseCaseFactory(repository: SettingsDataRepository): UserUseCaseFactory = UserUseCaseFactory(repository)
//
//   @Binds
//   @ActivityRetainedScoped
//   fun bindAuthUseCaseFactory(repository: AuthDataRepository): AuthUseCaseFactory = AuthUseCaseFactory(repository)
}