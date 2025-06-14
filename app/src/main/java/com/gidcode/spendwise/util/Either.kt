package com.gidcode.spendwise.util

fun <T> right(r: T) = Either.Right(r)
fun <T> left(l: T) = Either.Left(l)

sealed class Either<out L, out R> {
   data class Left<out L>(val l: L) : Either<L, Nothing>()
   data class Right<out R>(val r: R) : Either<Nothing, R>()

   fun isLeft(): Boolean = this is Left

   fun isRight(): Boolean = this is Right

   fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): Any? {
      return when (this) {
         is Left -> fnL(l)
         is Right -> fnR(r)
      }
   }

   fun getOrNull(): R? {
      return if (this is Right) {
         r
      } else {
         null
      }
   }
}