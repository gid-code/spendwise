package com.gidcode.spendwise.data.network.client

import android.content.Context
import com.gidcode.spendwise.data.network.AuthEventHandler
import com.gidcode.spendwise.data.network.AuthInterceptor
import com.gidcode.spendwise.data.network.ConnectionManager
import com.gidcode.spendwise.data.network.DefaultHeadersAdderRetrofitInterceptor
import com.gidcode.spendwise.data.network.NetworkStatusInterceptor
import com.gidcode.spendwise.data.network.constant.MyLadderApi
import com.gidcode.spendwise.data.network.service.SpendWiseService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_DEFAULT_CONFIGURATION = 15_000L

object MyLadderApiClient {
   fun createHttpClient(
      context: Context,
      authInterceptor: AuthInterceptor
   ): OkHttpClient {
      val httpClient = OkHttpClient.Builder()
         .addInterceptor(NetworkStatusInterceptor(ConnectionManager(context)))
         .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor)
         .addInterceptor(DefaultHeadersAdderRetrofitInterceptor())
         .addInterceptor(authInterceptor)
         .callTimeout(TIMEOUT_DEFAULT_CONFIGURATION, TimeUnit.SECONDS)

      return httpClient.build()
   }

   fun createSpendWiseService(
      client: OkHttpClient
   ): SpendWiseService {
      return Retrofit.Builder()
         .client(client)
         .baseUrl(MyLadderApi.BASE_URL)
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(SpendWiseService::class.java)
   }
}