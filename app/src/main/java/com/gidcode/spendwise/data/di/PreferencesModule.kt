package com.gidcode.spendwise.data.di

import com.gidcode.spendwise.data.datasource.local.Preferences
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesModule {

   @Binds
   abstract fun provideSpendWiseDataStore(preferences: SpendWiseDataStore): Preferences
}