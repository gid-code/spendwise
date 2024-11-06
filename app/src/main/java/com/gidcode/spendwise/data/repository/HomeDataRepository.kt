package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.HomeRemoteDataSource
import com.gidcode.spendwise.domain.model.Exception as Failure
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.util.Either
import com.gidcode.spendwise.util.left
import com.gidcode.spendwise.util.right

class HomeDataRepository(
   private val remoteDataSource: HomeRemoteDataSource,
   private val dataStore: SpendWiseDataStore
): HomeRepository {
   override suspend fun incomes(): Either<Failure, List<IncomeItemDomainModel>> {
      return try {
         val result = remoteDataSource.income(dataStore.getAccessToken()!!).map { incomeItemApi ->
            incomeItemApi.toDomain()
         }
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun expenses(): Either<Failure, List<ExpenseItemDomainModel>> {
      return try {
         val result = remoteDataSource.expenditure(dataStore.getAccessToken()!!).map { expenseItem ->
            expenseItem.toDomain()
         }
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

}