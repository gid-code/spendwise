package com.gidcode.spendwise.data.datasource.remote

import com.gidcode.spendwise.data.datasource.remote.model.ExpenseItemApi
import com.gidcode.spendwise.data.datasource.remote.model.IncomeItemApi
import com.gidcode.spendwise.data.network.service.SpendWiseService

class HomeRemoteDataSource(
   private val service: SpendWiseService,
) {

   suspend fun income(token: String): List<IncomeItemApi> {
      return service.income(token)
   }

   suspend fun expenditure(token: String): List<ExpenseItemApi> {
      return service.expenditure(token)
   }
}