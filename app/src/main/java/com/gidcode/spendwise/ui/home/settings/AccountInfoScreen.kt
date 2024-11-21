package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gidcode.spendwise.ui.common.PreviewContent

@Composable
fun AccountInfoScreen() {
   AccountInfoContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoContent(){
   Scaffold(
      topBar = {
         TopAppBar(title = {
            Text("Account Information")
         })
      }
   ) {padding->
      Column(
         modifier = Modifier.padding(vertical = padding.calculateTopPadding(), horizontal = 16.dp)
      ) {
         InfoTile(label= "Name", value= "John Doe")
         InfoTile(label= "Email", value= "johndoe@gmail.com")
      }
   }
}

@Composable
fun InfoTile(label: String,value: String) {
   Column(
      modifier = Modifier.padding(vertical = 8.dp)
   ) {
      Text(text = label,
         style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
         )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = value,
         style = TextStyle(
            fontSize = 18.sp,
         )
      )
      HorizontalDivider()
   }
}

@Preview(name = "Account Info")
@Composable
fun AccountInfoPreview() {
   PreviewContent {
      AccountInfoContent()
   }
}

@Preview(name = "Account Info Dark")
@Composable
fun AccountInfoDarkPreview() {
   PreviewContent(darkTheme = true) {
      AccountInfoContent()
   }
}