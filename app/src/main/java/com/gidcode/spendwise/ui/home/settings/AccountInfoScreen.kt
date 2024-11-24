package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun AccountInfoScreen() {
   val settingsViewModel = ViewModelProvider.settingsViewModel
   val user by settingsViewModel.user.collectAsState()
   AccountInfoContent(
      user
   )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoContent(user: User) {
   val navController = Navigator.current
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text("Account Information")
            },
            navigationIcon = {
               IconButton(
                  onClick = {
                     navController.popBackStack()
                  }
               ) {
                  Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "")
               }
            }
         )
      }
   ) {padding->
      Column(
         modifier = Modifier.padding(vertical = padding.calculateTopPadding(), horizontal = 16.dp)
      ) {
         InfoTile(label= "Name", value= user.name)
         InfoTile(label= "Email", value= user.email)
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
      AccountInfoContent(User("Mike Gooden","mikkey@gmail.com"))
   }
}

@Preview(name = "Account Info Dark")
@Composable
fun AccountInfoDarkPreview() {
   PreviewContent(darkTheme = true) {
      AccountInfoContent(User("Sam Moo","sammoo@g.com"))
   }
}