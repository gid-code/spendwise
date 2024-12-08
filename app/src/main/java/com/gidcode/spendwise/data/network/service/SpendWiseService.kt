package com.gidcode.spendwise.data.network.service

import com.gidcode.spendwise.data.datasource.remote.model.AddExpenseApiModel
import com.gidcode.spendwise.data.datasource.remote.model.AddIncomeApiModel
import com.gidcode.spendwise.data.datasource.remote.model.ExpenseItemApi
import com.gidcode.spendwise.data.datasource.remote.model.IncomeItemApi
import com.gidcode.spendwise.data.datasource.remote.model.LoginApiModel
import com.gidcode.spendwise.data.datasource.remote.model.LoginApiResponseModel
import com.gidcode.spendwise.data.network.constant.MyLadderApi
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiModel
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiResponseModel
import com.gidcode.spendwise.data.datasource.remote.model.UserApiResponseModel
import com.gidcode.spendwise.data.network.constant.ApiParameters.NO_AUTH_HEADER
import retrofit2.http.*


interface SpendWiseService {

   @POST(MyLadderApi.LOGIN)
   suspend fun login(
      @Body credentials: LoginApiModel,
      @Header(NO_AUTH_HEADER) authHeader: String = ""
   ): LoginApiResponseModel

   @POST(MyLadderApi.SIGNUP)
   suspend fun register(
      @Body request: SignUpApiModel,
      @Header(NO_AUTH_HEADER) authHeader: String = ""
   ): SignUpApiResponseModel

   @GET(MyLadderApi.INCOME)
   suspend fun income(
   ): List<IncomeItemApi>

   @GET(MyLadderApi.EXPENDITURE)
   suspend fun expenditure(
   ): List<ExpenseItemApi>

   @POST(MyLadderApi.INCOME)
   suspend fun addIncome(
      @Body request: AddIncomeApiModel
   )

   @POST(MyLadderApi.EXPENDITURE)
   suspend fun addExpense(
      @Body request: AddExpenseApiModel
   )

   @GET(MyLadderApi.USER_PROFILE)
   suspend fun user(
      @Path("userID") uuid: String
   ): UserApiResponseModel
}