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

   @GET(MyLadderApi.INCOME)
   suspend fun income(
      @Header(AUTHORIZATION_KEY) token: String
   ): List<IncomeItemApi>

   @GET(MyLadderApi.EXPENDITURE)
   suspend fun expenditure(
      @Header(AUTHORIZATION_KEY) token: String
   ): List<ExpenseItemApi>

   @POST(MyLadderApi.INCOME)
   suspend fun addIncome(
      @Header(AUTHORIZATION_KEY) token: String,
      @Body request: AddIncomeApiModel
   )

   @POST(MyLadderApi.EXPENDITURE)
   suspend fun addExpense(
      @Header(AUTHORIZATION_KEY) token: String,
      @Body request: AddExpenseApiModel
   )
}