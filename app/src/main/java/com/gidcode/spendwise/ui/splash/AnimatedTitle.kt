package com.gidcode.spendwise.ui.splash

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.gidcode.spendwise.R

@Composable
fun AnimatedTitle(visible : Boolean){
   val titleEnterTransition = fadeIn(
      animationSpec = tween(1000, 1600)
   ) + slideInVertically(
      initialOffsetY = { -100 },
      animationSpec = tween(1000, 1600)
   )

   AnimatedVisibility(
      visible = visible,
      enter = titleEnterTransition
   ) {
      Text(
         text = stringResource(R.string.app_name),
         style = MaterialTheme.typography.displayLarge,
         textAlign = TextAlign.Center
      )
   }
}