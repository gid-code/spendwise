package com.gidcode.spendwise.data.datasource.remote.model

import com.google.gson.annotations.SerializedName

data class SignUpApiResponseModel(
   @SerializedName("message"     ) var message     : String? = null,
)
