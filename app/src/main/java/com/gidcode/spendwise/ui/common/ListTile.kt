package com.gidcode.spendwise.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListTile(
   leading: Unit? = null,
   title: String,
   trailing: String
) {
   Box(
      modifier = Modifier.fillMaxWidth()
         .padding(bottom = 4.dp)
   ) {
      Row (
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween
      ){
         Row {
            leading
            if (leading != null)Spacer(modifier = Modifier.width(8.dp))
            Text(text = title,
               style = MaterialTheme.typography.titleMedium
            )
         }
         Text(text = trailing,
            style = MaterialTheme.typography.titleMedium.copy(
               color = MaterialTheme.colorScheme.primary,
               fontWeight = FontWeight.Bold
            )
         )
      }
   }
}

@Preview(name = "ListTile")
@Composable
fun ListTilePreview() {
   PreviewContent {
      ListTile(
         title = "Salary",
         trailing = "GHS 39.00"
      )
   }
}