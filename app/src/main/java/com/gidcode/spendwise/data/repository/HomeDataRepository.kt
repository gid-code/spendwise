package com.gidcode.spendwise.data.repository

import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.datasource.remote.HomeRemoteDataSource
import com.gidcode.spendwise.domain.model.AddExpenseDomainModel
import com.gidcode.spendwise.domain.model.AddIncomeDomainModel
import com.gidcode.spendwise.domain.model.Exception as Failure
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.util.Either
import com.gidcode.spendwise.util.left
import com.gidcode.spendwise.util.right
import kotlinx.coroutines.flow.Flow

class HomeDataRepository(
   private val remoteDataSource: HomeRemoteDataSource,
   private val localDataSource: SpendWiseDataStore
): HomeRepository {

   override suspend fun incomes(): Either<Failure, List<IncomeItemDomainModel>> {
      return try {
         val result = remoteDataSource.income().map { incomeItemApi ->
            incomeItemApi.user?.let { localDataSource.storeUserId(it) }
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
         val result = remoteDataSource.expenditure().map { expenseItem ->
            expenseItem.toDomain()
         }
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun addIncome(
      data: AddIncomeDomainModel
   ): Either<Failure, String> {
      return try {
         remoteDataSource.addIncome(data.toApi())
         right("Income Added")
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun addExpense(
      data: AddExpenseDomainModel
   ): Either<Failure, String> {
      return try {
         remoteDataSource.addExpense(data.toApi())
         right("Expense Added")
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

}