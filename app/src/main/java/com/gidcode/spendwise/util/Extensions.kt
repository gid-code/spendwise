package com.gidcode.spendwise.util

import android.content.Context
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.LocalTaxi
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp


fun Double.toStringAsFixed() : String{
   return String.format("%.2f",this)
}

fun String.getUniqueColor() : Color {
   val hash = this.hashCode()

   return Color((hash and 0xffffff) or 0xff00000)
}

fun String.getIconForItem(): ImageVector {
   val lowercaseItemName = this.lowercase()
   if (lowercaseItemName.contains("groceries")) {
      return Icons.Default.ShoppingCart
   } else if (lowercaseItemName.contains("restaurant")) {
      return Icons.Default.Restaurant
   } else if (lowercaseItemName.contains("snacks")) {
      return Icons.Default.Fastfood
   } else if (lowercaseItemName.contains("gas")) {
      return Icons.Default.LocalGasStation
   } else if (lowercaseItemName.contains("public transit")) {
      return Icons.Default.DirectionsBus
   } else if (lowercaseItemName.contains("taxi")) {
      return Icons.Default.LocalTaxi
   } else if (lowercaseItemName.contains("movies")) {
      return Icons.Default.Movie
   } else if (lowercaseItemName.contains("concerts")) {
      return Icons.Default.MusicNote
   } else if (lowercaseItemName.contains("book")) {
      return Icons.Default.Book
   } else if (lowercaseItemName.contains("pen")) {
      return Icons.Default.Edit
   } else if (lowercaseItemName.contains("rent")) {
      return Icons.Default.Home
   }
   else {
      return Icons.Default.AttachMoney
   }
}

fun Modifier.addMoveAnimation(orientation: Orientation, from: Dp, to: Dp, duration: Int): Modifier =
   composed {
      var contentOffset by remember { mutableStateOf(from) }
      val animatedContentOffset by animateDpAsState(
         targetValue = contentOffset,
         animationSpec = TweenSpec(
            durationMillis = duration,
         )
      ).also {
         contentOffset = to
      }
      when (orientation) {
         is Orientation.Vertical -> this.offset(y = animatedContentOffset)
         is Orientation.Horizontal -> this.offset(x = animatedContentOffset)
      }
   }

fun Modifier.addFadeAnimation(from: Float, to: Float, duration: Int): Modifier = composed {
   var contentAlpha by remember { mutableStateOf(from) }
   val animatedContentAlpha by animateFloatAsState(
      targetValue = contentAlpha,
      animationSpec = TweenSpec(
         durationMillis = duration,
      )
   ).also {
      contentAlpha = to
   }
   this.alpha(animatedContentAlpha)
}

fun Context.getActivity(): AppCompatActivity? {
   var currentContext = this
   while (currentContext is ContextWrapper) {
      if (currentContext is AppCompatActivity) {
         return currentContext
      }
      currentContext = currentContext.baseContext
   }
   return null
}
