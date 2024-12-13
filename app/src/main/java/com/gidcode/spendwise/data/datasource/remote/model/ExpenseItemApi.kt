package com.gidcode.spendwise.data.datasource.remote.model

import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.google.gson.annotations.SerializedName

data class ExpenseItemApi(
   @SerializedName("id"     ) var id     : String? = null,
   @SerializedName("category") var category : String? = null,
   @SerializedName("estimatedAmount") var estimatedAmount : Int? = null,
   @SerializedName("nameOfItem") var nameOfItem : String? = null
){
   fun toDomain() = ExpenseItemDomainModel(category.orEmpty(), nameOfItem.orEmpty(), estimatedAmount ?: -1)
}
