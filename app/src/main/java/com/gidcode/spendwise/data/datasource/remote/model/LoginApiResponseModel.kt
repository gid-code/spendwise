package com.gidcode.spendwise.data.datasource.remote.model

import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.google.gson.annotations.SerializedName

data class LoginApiResponseModel (
   @SerializedName("message"     ) var message     : String? = null,
   @SerializedName("accessToken" ) var accessToken : String? = null,
   @SerializedName("expiresIn"   ) var expiresIn   : String? = null
){
   fun toAccessTokenModel() = AccessTokenDomainModel(accessToken!!)
}