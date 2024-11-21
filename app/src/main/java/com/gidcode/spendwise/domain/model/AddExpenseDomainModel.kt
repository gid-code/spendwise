package com.gidcode.spendwise.domain.model

import com.gidcode.spendwise.data.datasource.remote.model.AddExpenseApiModel

data class AddExpenseDomainModel(val name: String, val category: String, val amount: Double) {
   fun toApi() = AddExpenseApiModel(name,category,amount)
}
