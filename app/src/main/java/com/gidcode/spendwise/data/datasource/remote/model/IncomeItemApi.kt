package com.gidcode.spendwise.data.datasource.remote.model

import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.google.gson.annotations.SerializedName

data class IncomeItemApi(
   @SerializedName("id"     ) var id     : String? = null,
   @SerializedName("nameOfRevenue") var nameOfRevenue : String? = null,
   @SerializedName("amount") var amount : Int? = null,
   @SerializedName("user") var user : String? = null
) {
   fun toDomain() = IncomeItemDomainModel(nameOfRevenue!!, amount!!)
}
