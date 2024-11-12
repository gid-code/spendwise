package com.gidcode.spendwise.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.auth.LoginScreenContent
import kotlinx.coroutines.delay

@Composable
fun ErrorViewWithoutButton(
   text: String,
   visible: Boolean,
   onDismiss: () -> Unit
) {
   val shape = RoundedCornerShape(20)
   LaunchedEffect(Unit) {
      delay(3000)
      onDismiss()
   }

   AnimatedVisibility(
      visible = visible,
      enter = slideInVertically(initialOffsetY = { +40 }) +
          fadeIn(),
      exit = slideOutVertically() + fadeOut()
   ) {
      Box(
         modifier = Modifier
            .padding(horizontal = 12.dp)
            .clip(shape)
            .background(
               color = MaterialTheme.colorScheme.errorContainer,
            )
            .border(
               width = 1.dp,
               color = MaterialTheme.colorScheme.error,
               shape = shape,
            )
      ) {
         Row(
            modifier = Modifier
               .padding(all = 8.dp)
               .fillMaxWidth(),
            verticalAlignment = Alignment.Top
         ) {
            Icon(
               imageVector = Icons.Default.Info,
               tint = MaterialTheme.colorScheme.error,
               contentDescription = "warning icon"
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
               text,
               style = MaterialTheme.typography.bodyMedium.copy(
                  color = MaterialTheme.colorScheme.onErrorContainer
               ),
               overflow = TextOverflow.Visible
            )
         }
      }
   }
}

@Preview(name = "Error view")
@Composable
fun ErrorViewWOButtonPreview() {
   PreviewContent {
      ErrorViewWithoutButton(text = "Error processing your request Error processing your request Error processing your request Error processing your request",visible = true) {

      }
   }
}

@Preview(name = "Error view (Dark)")
@Composable
fun ErrorViewWOButtonDarkPreview() {
   PreviewContent(darkTheme = true) {
      ErrorViewWithoutButton(text = "Error processing your request", visible = true) {

      }
   }
}