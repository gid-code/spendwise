package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun NotificationScreen() {
   NotificationContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationContent() {
   val navController = Navigator.current
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text("Notifications")
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
   ){contentPadding->
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(vertical = contentPadding.calculateTopPadding(), horizontal = 16.dp),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally
      ){
         Icon(
            imageVector = Icons.Default.NotificationsOff,
            contentDescription = "notification icon",
            tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
            modifier = Modifier.size(75.dp)
         )
         Spacer(modifier = Modifier.height(16.dp))
         Text(
            text = "No notifications available at the moment",
            style = MaterialTheme.typography.titleLarge.copy(
               color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
            ),
            textAlign = TextAlign.Center
         )
      }
   }
}

@Preview(name = "Notification ")
@Composable
fun NotificationScreenPreview() {
   PreviewContent {
      NotificationContent()
   }
}

@Preview(name = "Notification Dark")
@Composable
fun NotificationScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      NotificationContent()
   }
}
