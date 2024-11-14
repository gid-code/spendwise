package com.gidcode.spendwise.util

import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp


fun Double.toStringAsFixed() : String{
   return String.format("%.2f",this)
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
