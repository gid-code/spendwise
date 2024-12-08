package com.gidcode.spendwise.data.datasource.remote

import com.gidcode.spendwise.data.datasource.remote.model.AddExpenseApiModel
import com.gidcode.spendwise.data.datasource.remote.model.AddIncomeApiModel
import com.gidcode.spendwise.data.datasource.remote.model.ExpenseItemApi
import com.gidcode.spendwise.data.datasource.remote.model.IncomeItemApi
import com.gidcode.spendwise.data.network.service.SpendWiseService

class HomeRemoteDataSource(
   private val service: SpendWiseService,
) {

   suspend fun income(): List<IncomeItemApi> {
      return service.income()
   }

   suspend fun expenditure(): List<ExpenseItemApi> {
      return service.expenditure()
   }

   suspend fun addIncome(data: AddIncomeApiModel) {
      return service.addIncome(data)
   }

   suspend fun addExpense(data: AddExpenseApiModel) {
      return service.addExpense(data)
   }
}