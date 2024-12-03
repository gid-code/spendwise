package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun SecurityScreen(){
   val settingsViewModel = ViewModelProvider.settingsViewModel
   val isBiometricEnabled by settingsViewModel.isBiometricEnabled.collectAsState()
   SecurityScreenContent(
      isBiometricEnabled
   ){
      settingsViewModel.toggleBiometric()
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityScreenContent(isBiometricEnabled: Boolean, toggleBiometric: () -> Unit) {
   val navController = Navigator.current
//   var checked: Boolean by remember { mutableStateOf(isBiometricEnabled) }
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text("Security")
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
   ) { contentPadding ->
      Column(
         modifier = Modifier
            .fillMaxSize()
            .padding(vertical = contentPadding.calculateTopPadding(), horizontal = 16.dp),
         horizontalAlignment = Alignment.CenterHorizontally
      ){
         ItemTile(
            title = "Enable Pin",
            icon = Icons.Outlined.Lock
         ) { }
         ItemTile(
            title = "Enable Biometric",
            icon = Icons.Outlined.Fingerprint,
            trailing = {
               Switch(
                  checked = isBiometricEnabled,
                  onCheckedChange = { toggleBiometric() }
               )
            }
         ) { toggleBiometric() }
      }
   }
}

@Composable
fun ItemTile(title: String, icon: ImageVector, trailing: @Composable (() -> Unit?)? = null, onClick: ()->Unit){
   Row(
      modifier = Modifier
         .fillMaxWidth()
         .padding(vertical = 8.dp)
         .clickable {
            onClick()
         }
      ,
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
   ) {
      Row {
         Icon(imageVector = icon, contentDescription = "tile icon")
         Spacer(modifier = Modifier.width(8.dp))
         Text(text = title)
      }
      trailing?.let { it() }
   }
}

@Preview(name = "Security")
@Composable
fun SecurityScreenPreview() {
   PreviewContent {
      SecurityScreenContent(false) {

      }
   }
}

@Preview(name = "Security Dark")
@Composable
fun SecurityScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      SecurityScreenContent(true) {

      }
   }
}