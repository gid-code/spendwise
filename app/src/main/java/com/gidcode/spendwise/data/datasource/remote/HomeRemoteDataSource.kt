package com.gidcode.spendwise.data.datasource.remote

import com.gidcode.spendwise.data.datasource.remote.model.AddExpenseApiModel
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

   suspend fun addIncome(token: String,data: AddIncomeApiModel) {
      val auth = "Bearer $token"
      return service.addIncome(auth,data)
   }

   suspend fun addExpense(token: String,data: AddExpenseApiModel) {
      val auth = "Bearer $token"
      return service.addExpense(auth,data)
   }
}