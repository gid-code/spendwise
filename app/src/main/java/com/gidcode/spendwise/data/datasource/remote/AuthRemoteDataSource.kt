package com.gidcode.spendwise.data.datasource.remote

import com.gidcode.spendwise.data.datasource.remote.model.LoginApiModel
import com.gidcode.spendwise.data.datasource.remote.model.LoginApiResponseModel
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiModel
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiResponseModel
import com.gidcode.spendwise.data.datasource.remote.model.UserApiResponseModel
import com.gidcode.spendwise.data.network.service.SpendWiseService
class AuthRemoteDataSource(
   private val service: SpendWiseService,
) {
   suspend fun login(credentials: LoginApiModel):LoginApiResponseModel {
      return service.login(credentials)
   }

   suspend fun createUser(request: SignUpApiModel): SignUpApiResponseModel {
      return service.register(request)
   }

   suspend fun user(uuid: String, token: String): UserApiResponseModel {
      val auth = "Bearer $token"
      return service.user(uuid,auth)
   }
}