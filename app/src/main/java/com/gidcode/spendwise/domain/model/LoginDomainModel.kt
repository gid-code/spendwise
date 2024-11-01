package com.gidcode.spendwise.domain.model

import com.gidcode.spendwise.data.datasource.remote.model.LoginApiModel

data class LoginDomainModel( val email: String, val password: String){
   fun toApi() = LoginApiModel(email,password)
}