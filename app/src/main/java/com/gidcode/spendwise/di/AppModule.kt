package com.gidcode.spendwise.di

import android.content.Context
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.AuthRemoteDataSource
import com.gidcode.spendwise.data.network.client.MyLadderApiClient
import com.gidcode.spendwise.data.network.service.SpendWiseService
import com.gidcode.spendwise.data.repository.AuthDataRepository
import com.gidcode.spendwise.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
   @Provides
   fun provideHttpClient(
      @ApplicationContext context: Context
   ): OkHttpClient = MyLadderApiClient.createHttpClient(context)

   @Provides
   @Singleton
   fun provideSpendWiseService(
      client: OkHttpClient
   ): SpendWiseService = MyLadderApiClient.createSpendWiseService(client)

   @Provides
   @Singleton
   fun provideSpendWiseDataStore(
      @ApplicationContext context: Context
   ): SpendWiseDataStore = SpendWiseDataStore(context)

   @Provides
   @Singleton
   fun provideAuthRemoteDataSource(
      service: SpendWiseService
   ): AuthRemoteDataSource = AuthRemoteDataSource(service)

   @Provides
   @Singleton
   fun provideAuthRepository(
      dataSource: AuthRemoteDataSource,
      dataStore: SpendWiseDataStore
   ): AuthRepository = AuthDataRepository(dataSource,dataStore)
}