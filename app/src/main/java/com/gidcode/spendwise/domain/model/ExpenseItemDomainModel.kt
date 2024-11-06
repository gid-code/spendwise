package com.gidcode.spendwise.domain.model

data class ExpenseItemDomainModel(
   val category: String,
   val nameOfItem: String,
   val estimatedAmount: Int
)
