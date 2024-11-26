package com.gidcode.spendwise.data.datasource.remote.model

import com.gidcode.spendwise.domain.model.User
import com.google.gson.annotations.SerializedName

data class UserApiResponseModel(
   @SerializedName("name"     ) val name     : String ,
   @SerializedName("email" ) val email : String,
){
   fun toDomain(): User = User(name,email)
}
