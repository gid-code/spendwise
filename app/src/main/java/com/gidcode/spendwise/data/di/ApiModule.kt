package com.gidcode.spendwise.data.di

import android.content.Context
import com.gidcode.spendwise.data.datasource.local.Preferences
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.network.client.MyLadderApiClient
import com.gidcode.spendwise.data.network.interceptor.AuthenticationInterceptor
import com.gidcode.spendwise.data.network.service.SpendWiseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
   @Provides
   fun provideHttpClient(
      @ApplicationContext context: Context,
      authInterceptor: AuthenticationInterceptor
   ): OkHttpClient = MyLadderApiClient.createHttpClient(context,authInterceptor)

   @Provides
   @Singleton
   fun provideSpendWiseService(
      client: OkHttpClient
   ): SpendWiseService = MyLadderApiClient.createSpendWiseService(client)

   @Provides
   fun provideAuthenticationInterceptor(
      preference: Preferences
   ): AuthenticationInterceptor = AuthenticationInterceptor(preference)
}