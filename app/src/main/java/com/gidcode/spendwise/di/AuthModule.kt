package com.gidcode.spendwise.di

import com.gidcode.spendwise.data.network.AuthEventHandler
import com.gidcode.spendwise.ui.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
abstract class AuthModule {

   @Binds
   @Singleton
   abstract fun bindAuthEventHandler(
      authViewModel: HomeViewModel
   ): AuthEventHandler
}