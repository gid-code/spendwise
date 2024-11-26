package com.gidcode.spendwise.domain.repository

import com.gidcode.spendwise.domain.model.AddExpenseDomainModel
import com.gidcode.spendwise.domain.model.AddIncomeDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.util.Either
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
   suspend fun incomes(token: String): Either<Exception, List<IncomeItemDomainModel>>
   suspend fun expenses(token: String): Either<Exception, List<ExpenseItemDomainModel>>
   suspend fun addIncome(token: String, data: AddIncomeDomainModel): Either<Exception,String>
   suspend fun addExpense(token: String, data: AddExpenseDomainModel): Either<Exception,String>
   fun getAccessToken(): Flow<String?>
   suspend fun clearAccessToken()
   suspend fun clearUserId()
   suspend fun clearUser()
}