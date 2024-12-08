package com.gidcode.spendwise.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class User(val name: String, val email: String){
   fun firstname(): String = name.split(' ').first()
   fun toJsonString(): String{
      val json: Json = Json
      return json.encodeToString(this)
   }
}
