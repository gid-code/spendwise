package com.gidcode.spendwise.data.datasource.remote.model

import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.google.gson.annotations.SerializedName
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class LoginApiResponseModel (
   @SerializedName("message"     ) var message     : String? = null,
   @SerializedName("accessToken" ) var accessToken : String? = null,
   @SerializedName("expiresIn"   ) var expiresIn   : String? = null
){

   @Transient
   private val requestedAt: Instant = Instant.now()

   fun toAccessTokenModel() = AccessTokenDomainModel(accessToken!!, expiresAt)

   private val expiresAt: Long?
      get() {
         if (expiresIn == null) return 0L

         return parseExpiresIn(expiresIn!!)?.inWholeSeconds?.let { requestedAt.plusSeconds(it).epochSecond }
      }

   private fun parseExpiresIn(expiresIn: String): Duration? {
      val regex = Regex("""(\d+)\s*(hours?|minutes?|seconds?)""", RegexOption.IGNORE_CASE)
      val matchResult = regex.matchEntire(expiresIn.trim())

      return if (matchResult != null) {
         val (value, unit) = matchResult.destructured
         val durationValue = value.toLongOrNull()
         if (durationValue != null) {
            when (unit.lowercase()) {
               "hour", "hours" -> durationValue.toDuration(DurationUnit.HOURS)
               "minute", "minutes" -> durationValue.toDuration(DurationUnit.MINUTES)
               "second", "seconds" -> durationValue.toDuration(DurationUnit.SECONDS)
               else -> null
            }
         } else null
      } else null
   }
}