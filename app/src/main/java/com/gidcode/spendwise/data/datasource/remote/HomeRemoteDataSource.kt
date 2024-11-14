package com.gidcode.spendwise.data.datasource.remote

import com.gidcode.spendwise.data.datasource.remote.model.AddIncomeApiModel
import com.gidcode.spendwise.data.datasource.remote.model.ExpenseItemApi
import com.gidcode.spendwise.data.datasource.remote.model.IncomeItemApi
import com.gidcode.spendwise.data.network.service.SpendWiseService

class HomeRemoteDataSource(
   private val service: SpendWiseService,
) {

   suspend fun income(token: String): List<IncomeItemApi> {
      val auth = "Bearer $token"
      return service.income(auth)
   }

   suspend fun expenditure(token: String): List<ExpenseItemApi> {
      val auth = "Bearer $token"
      return service.expenditure(auth)
   }

   suspend fun addIncome(token: String,data: AddIncomeApiModel): String {
      val auth = "Bearer $token"
      return service.addIncome(auth,data)
   }
}