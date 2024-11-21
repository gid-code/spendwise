package com.gidcode.spendwise.data.repository

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

class HomeDataRepository(
   private val remoteDataSource: HomeRemoteDataSource
): HomeRepository {

   override suspend fun incomes(token: String): Either<Failure, List<IncomeItemDomainModel>> {
      return try {
         val result = remoteDataSource.income(token).map { incomeItemApi ->
            incomeItemApi.toDomain()
         }
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun expenses(token: String): Either<Failure, List<ExpenseItemDomainModel>> {
      return try {
         val result = remoteDataSource.expenditure(token).map { expenseItem ->
            expenseItem.toDomain()
         }
         right(result)
      }catch (e: Exception){
         println(e.message)
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun addIncome(
      token: String,
      data: AddIncomeDomainModel
   ): Either<com.gidcode.spendwise.domain.model.Exception, String> {
      return try {
         remoteDataSource.addIncome(token,data.toApi())
         right("Income Added")
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

   override suspend fun addExpense(
      token: String,
      data: AddExpenseDomainModel
   ): Either<com.gidcode.spendwise.domain.model.Exception, String> {
      return try {
         remoteDataSource.addExpense(token,data.toApi())
         right("Expense Added")
      }catch (e: Exception){
         left(Failure.General(e.localizedMessage))
      }
   }

}