package com.gidcode.spendwise.data.datasource.remote.model

data class SignUpApiModel(val name: String, val email:String, val password:String) {
   fun toLoginApiModel() = LoginApiModel(email,password)
}