package com.gidcode.spendwise.data.network.interceptor

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.network.constant.ApiParameters
import com.gidcode.spendwise.data.network.constant.MyLadderApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import java.time.Instant

@RunWith(RobolectricTestRunner::class)
class AuthenticationInterceptorTest {
   private lateinit var preferences: SpendWiseDataStore
   private lateinit var mockWebServer: MockWebServer
   private lateinit var authenticationInterceptor: AuthenticationInterceptor
   private lateinit var okHttpClient: OkHttpClient

   private val endpointSeparator = "/"
   private val incomeEndpointPath = endpointSeparator + MyLadderApi.INCOME
   private val validToken = "validToken"
   private val expiredToken = "expiredToken"

   @Before
   fun setup() {
      preferences = mock(SpendWiseDataStore::class.java)

      mockWebServer = MockWebServer()
      mockWebServer.start(8080)

      authenticationInterceptor = AuthenticationInterceptor(preferences)
      okHttpClient = OkHttpClient()
         .newBuilder()
         .addInterceptor(authenticationInterceptor)
         .build()
   }

   @After
   fun teardown() {
      mockWebServer.shutdown()
   }

   @Test
   fun authenticationInterceptor_validToken() {
      // Given
      `when`(runBlocking { preferences.getAccessToken().first() }).thenReturn(expiredToken)
      `when`(runBlocking{ preferences.getTokenExpireAt().first() }).thenReturn(
         Instant.now().plusSeconds(7200).epochSecond
      )
      mockWebServer.dispatcher = getDispatcherForValidToken()

      // When
      okHttpClient.newCall(
         Request.Builder()
            .url(mockWebServer.url(MyLadderApi.INCOME))
            .build()
      ).execute()

      // Then
      val request = mockWebServer.takeRequest()

      with(request) {
         assertThat(method).isEqualTo("GET")
         assertThat(path).isEqualTo(incomeEndpointPath)
         assertThat(getHeader(ApiParameters.AUTH_HEADER))
            .isEqualTo(ApiParameters.TOKEN_TYPE + validToken)
      }
   }

   @Test
   fun authenticationInterceptor_expiredToken() {
      // Given
      `when`(runBlocking { preferences.getAccessToken().first() }).thenReturn(validToken)
      `when`(runBlocking{ preferences.getTokenExpireAt().first() }).thenReturn(
         Instant.now().minusSeconds(7200).epochSecond
      )
//      `when`(runBlocking { preferences.clearUserAccessInfo() }).thenReturn(null)
      mockWebServer.dispatcher = getDispatcherForExpiredToken()

      // When
      okHttpClient.newCall(
         Request.Builder()
            .url(mockWebServer.url(MyLadderApi.INCOME))
            .build()
      ).execute()

      // Then
      val request = mockWebServer.takeRequest()

      val inOrder = inOrder(preferences)

      inOrder.verify(preferences).getAccessToken()
      runBlocking { inOrder.verify(preferences).clearUserAccessInfo() }

      verify(preferences, times(1)).getAccessToken()
      verify(preferences, times(1)).getTokenExpireAt()
      runBlocking { verify(preferences, times(1)).clearUserAccessInfo() }
      verifyNoMoreInteractions(preferences)

      with(request) {
         assertThat(method).isEqualTo("GET")
         assertThat(path).isEqualTo(incomeEndpointPath)
         assertThat(getHeader(ApiParameters.AUTH_HEADER))
            .isEqualTo(ApiParameters.TOKEN_TYPE + validToken)
      }
   }

   private fun getDispatcherForValidToken() = object : Dispatcher() {
      override fun dispatch(request: RecordedRequest): MockResponse {
         return when (request.path) {
            incomeEndpointPath -> { MockResponse().setResponseCode(200) }
            else -> { MockResponse().setResponseCode(404) }
         }
      }
   }

   private fun getDispatcherForExpiredToken() = object : Dispatcher() {
      override fun dispatch(request: RecordedRequest): MockResponse {
         return when (request.path) {
            incomeEndpointPath -> { MockResponse().setResponseCode(401) }
            else -> { MockResponse().setResponseCode(404) }
         }
      }
   }
}


