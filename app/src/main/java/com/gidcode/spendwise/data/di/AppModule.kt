package com.gidcode.spendwise.data.di

import android.content.Context
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.AuthRemoteDataSource
import com.gidcode.spendwise.data.datasource.remote.HomeRemoteDataSource
import com.gidcode.spendwise.data.network.interceptor.AuthInterceptor
import com.gidcode.spendwise.data.network.client.MyLadderApiClient
import com.gidcode.spendwise.data.network.service.SpendWiseService
import com.gidcode.spendwise.data.repository.AuthDataRepository
import com.gidcode.spendwise.data.repository.HomeDataRepository
import com.gidcode.spendwise.data.repository.SettingsDataRepository
import com.gidcode.spendwise.data.di.usecasefactory.AuthUseCaseFactory
import com.gidcode.spendwise.data.di.usecasefactory.UserUseCaseFactory
import com.gidcode.spendwise.data.network.interceptor.AuthenticationInterceptor
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.domain.repository.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {

//   @Provides
//   fun provideAuthRemoteDataSource(
//      service: SpendWiseService
//   ): AuthRemoteDataSource = AuthRemoteDataSource(service)

//   @Provides
//   fun provideAuthRepository(
//      dataSource: AuthRemoteDataSource,
//      dataStore: SpendWiseDataStore
//   ): AuthRepository = AuthDataRepository(dataSource,dataStore)

//   @Provides
//   fun provideHomeRemoteDataSource(
//      service: SpendWiseService
//   ): HomeRemoteDataSource = HomeRemoteDataSource(service)

//   @Provides
//   fun provideHomeRepository(
//      dataSource: HomeRemoteDataSource,
//      dataStore: SpendWiseDataStore
//   ): HomeRepository = HomeDataRepository(dataSource,dataStore)
//
//   @Provides
//   fun provideSettingsRepository(
//      remoteDataSource: AuthRemoteDataSource,
//      dataSource: SpendWiseDataStore
//   ): SettingsRepository = SettingsDataRepository(remoteDataSource,dataSource)

//   @Provides
//   fun providesUserUseCaseFactory(
//      repository: SettingsRepository
//   ): UserUseCaseFactory = UserUseCaseFactory(repository)
//
//   @Provides
//   fun providesAuthUseCaseFactory(
//   ): AuthUseCaseFactory = AuthUseCaseFactory

//}