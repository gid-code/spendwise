package com.gidcode.spendwise.domain.repository

import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.util.Either

interface HomeRepository {
   suspend fun incomes(): Either<Exception, List<IncomeItemDomainModel>>
   suspend fun expenses(): Either<Exception, List<ExpenseItemDomainModel>>
}