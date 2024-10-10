package com.gidcode.spendwise.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun AnimatedImage(
   visible : Boolean,
   enterTransition: EnterTransition,
   image: Int,
   description: String,
   modifier: Modifier
){

   AnimatedVisibility(
      visible = visible,
      enter = enterTransition,
   ) {
      Image(
         painter = painterResource(image),
         contentDescription = description,
         modifier
      )
   }
}