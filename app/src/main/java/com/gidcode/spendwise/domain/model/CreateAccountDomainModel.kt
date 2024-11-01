package com.gidcode.spendwise.domain.model

import com.gidcode.spendwise.data.datasource.remote.model.LoginApiModel
import com.gidcode.spendwise.data.datasource.remote.model.SignUpApiModel

data class CreateAccountDomainModel(val name: String, val email: String, val password: String){
   fun toApi() = SignUpApiModel(name,email,password)
   fun toLoginDomain() = LoginDomainModel(email,password)
}
