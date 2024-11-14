package com.gidcode.spendwise.domain.model

import com.gidcode.spendwise.data.datasource.remote.model.AddIncomeApiModel

data class AddIncomeDomainModel(val name: String, val amount: Double) {
   fun toApi() = AddIncomeApiModel(nameOfRevenue = name,amount)
}