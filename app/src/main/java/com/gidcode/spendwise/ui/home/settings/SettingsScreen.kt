package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.theme.otherGradientColor

@Composable
fun SettingsScreen(){
   val settingsViewModel = ViewModelProvider.settingsViewModel
   val user by settingsViewModel.user.collectAsState()
   SettingsScreenContent(
      user
   )
}

@Composable
fun SettingsScreenContent(user: User) {
   val navController = Navigator.current
   Surface(
      modifier = Modifier
         .fillMaxSize()
   ){
      Box(modifier = Modifier.fillMaxSize()){
         Box(
            modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight(0.25f)
               .background(
                  brush = Brush.linearGradient(
                     colors = listOf(MaterialTheme.colorScheme.primary, otherGradientColor),
                  ),
                  shape = RoundedCornerShape(
                     topEnd = 0.dp,
                     topStart = 0.dp,
                     bottomStart = 50.dp,
                     bottomEnd = 50.dp
                  )
               )
         )
         Column(
            modifier = Modifier
               .fillMaxSize()
               .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.16f))
            Box(modifier = Modifier
               .size(100.dp)
               .background(color = MaterialTheme.colorScheme.onSurface, shape = CircleShape)
            )
            {
               Icon(
                  imageVector = Icons.Default.Person,
                  contentDescription = "cake",
                  modifier = Modifier
                     .align(Alignment.Center)
                     .size(60.dp),
                  tint = MaterialTheme.colorScheme.primary
               )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = user.name,
               style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = user.email,
               style = MaterialTheme.typography.bodyMedium.copy(
                  color = Color.Gray.copy(alpha = 0.6f)
               )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxSize()){
               Column(modifier =
               Modifier
                  .verticalScroll(rememberScrollState())
               ) {
                  SettingsSection(
                     title = "Account",
                     icon = Icons.Default.Person,
                     tiles = {
                        SettingsTile(
                           title = "Account Information",
                           icon = Icons.Outlined.Info,
                           onClick = { navController.navigate(Destination.AccountInfo.route) }
                        )
                     }
                  )
                  SettingsSection(
                     title = "Preferences",
                     icon = Icons.Default.Settings,
                     tiles = {
                        SettingsTile(
                           title = "Appearance",
                           icon = Icons.Default.Palette,
                           onClick = { navController.navigate(Destination.Appearance.route) }
                        )
                        SettingsTile(
                           title = "Notifications",
                           icon = Icons.Default.Notifications,
                           onClick = { navController.navigate(Destination.Notifications.route) }
                        )
                     }
                  )
                  SettingsSection(
                     title = "Security",
                     icon = Icons.Default.Security,
                     tiles = {
                        SettingsTile(
                           title = "Privacy and Security",
                           icon = Icons.Default.Lock,
                           onClick = { navController.navigate(Destination.Privacy.route) }
                        )
                     }
                  )
                  SettingsSection(
                     title = "About",
                     icon = Icons.Default.Info,
                     tiles = {
                        SettingsTile(
                           title = "About Us",
                           icon = Icons.Default.Business,
                           onClick = { }
                        )
                     }
                  )
                  Spacer(modifier = Modifier.height(20.dp))
                  ElevatedButton(
                     onClick = {
                        //open confirmation dialog
                     },
                     colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color.Red,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray
                     ),
                     modifier = Modifier
                        .fillMaxWidth(),
                     shape = RoundedCornerShape(8.dp)
                  ) {
                     Text("Logout",
                     )
                  }
               }
            }
         }
      }
   }
}

@Composable
fun SettingsSection(title: String, icon: ImageVector, tiles: @Composable ()-> Unit) {
   Column {
      Row(
         modifier = Modifier.padding(0.dp,16.dp,16.dp,8.dp)
      ) {
         Icon(imageVector = icon, contentDescription = "section icon", tint = MaterialTheme.colorScheme.primary)
         Spacer(modifier = Modifier.width(8.dp))
         Text(text = title, style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold))
      }
      tiles()
      HorizontalDivider()
   }
}

@Composable
fun SettingsTile(title: String, icon: ImageVector, onClick: ()->Unit){
   Row(
      modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 8.dp)
         .clickable {
//            var route = ""
//            if (title == "Account Information") {
//               route = Destination.AccountInfo.route
//            }else if (title == "Appearance") {
//               route = Destination.Appearance.route
//            }
            onClick()
         }
      ,
      horizontalArrangement = Arrangement.SpaceBetween
   ) {
      Row {
         Icon(imageVector = icon, contentDescription = "tile icon")
         Spacer(modifier = Modifier.width(8.dp))
         Text(text = title)
      }
      Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "arrow")
   }
}


@Preview(name = "Settings")
@Composable
fun SettingsScreenPreview() {
   PreviewContent {
      SettingsScreenContent(User("Eric Gordon", "ericgordon@gmail.com"))
   }
}

@Preview(name = "Settings Dark")
@Composable
fun SettingsScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      SettingsScreenContent(User("Mo Mo","sendmono@gmail.com"))
   }
}
