package com.gidcode.spendwise.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.auth.LoginScreenContent
import kotlinx.coroutines.delay

@Composable
fun ErrorViewWithoutButton(
   text: String,
   onDismiss: () -> Unit
) {
   LaunchedEffect(Unit) {
      delay(3000)
      onDismiss()
   }
   Box(
      modifier = Modifier
         .padding(horizontal = 24.dp)
         .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.error,
            shape = RoundedCornerShape(20),
         )
         .background(
            color = MaterialTheme.colorScheme.errorContainer,
            shape = RoundedCornerShape(20)
         )
   ) {
      Column(
         modifier = Modifier.padding(all = 8.dp)
            .fillMaxWidth(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Text(
            text,
            style = MaterialTheme.typography.bodyMedium.copy(
               color = MaterialTheme.colorScheme.onErrorContainer
            )
         )
      }
   }
}

@Preview(name = "Error view")
@Composable
fun ErrorViewWOButtonPreview() {
   PreviewContent {
      ErrorViewWithoutButton(text = "Error processing your request") {

      }
   }
}

@Preview(name = "Error view (Dark)")
@Composable
fun ErrorViewWOButtonDarkPreview() {
   PreviewContent(darkTheme = true) {
      ErrorViewWithoutButton(text = "Error processing your request") {

      }
   }
}