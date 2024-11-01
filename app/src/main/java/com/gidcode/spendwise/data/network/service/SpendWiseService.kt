package com.gidcode.spendwise.data.network.service

import com.gidcode.spendwise.data.datasource.remote.model.LoginApiModel
import com.gidcode.spendwise.data.datasource.remote.model.LoginApiResponseModel
import com.gidcode.spendwise.data.network.constant.MyLadderApi
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiModel
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiResponseModel
import retrofit2.http.*

private const val AUTHORIZATION_KEY = "Authorization"

interface SpendWiseService {

   @POST(MyLadderApi.LOGIN)
   suspend fun login(
      @Body credentials: LoginApiModel
   ): LoginApiResponseModel

   @POST(MyLadderApi.SIGNUP)
   suspend fun register(
      @Body request: SignUpApiModel
   ): SignUpApiResponseModel
}