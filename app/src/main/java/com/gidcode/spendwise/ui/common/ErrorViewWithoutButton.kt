package com.gidcode.spendwise.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorViewWithoutButton(
   text: String,
) {
   Column(
      modifier = Modifier.padding(horizontal = 24.dp)
   ) {
      Text(
         text,
         modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
      )
   }
}