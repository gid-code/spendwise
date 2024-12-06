package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.R
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun ConfirmLogoutDialog(){
   val settingsViewModel = ViewModelProvider.settingsViewModel
   ConfirmLogoutContent(){
      settingsViewModel.logout()
   }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmLogoutContent(logout: () -> Unit) {
   val navController = Navigator.current

   BasicAlertDialog(onDismissRequest = { navController.popBackStack() }){
      Column(
         Modifier
            .background(
               color = MaterialTheme.colorScheme.surface,
               shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp, vertical = 10.dp),
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         Spacer(modifier = Modifier.height(8.dp))

         Text(text = stringResource(R.string.confirm_logout_title),
            style = MaterialTheme.typography.titleLarge.copy(
               color = MaterialTheme.colorScheme.onSurface
            )
         )
         Spacer(modifier = Modifier.height(24.dp))

         Text(text = stringResource(R.string.confirm_logout_text),
            style = MaterialTheme.typography.bodyMedium.copy(
               color = MaterialTheme.colorScheme.onSurface
            )
         )

         Spacer(modifier = Modifier.height(24.dp))

         Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
         ) {
            TextButton(onClick = { navController.popBackStack() }) {
               Text(text = stringResource(R.string.cancel_btn_text),
                  style = MaterialTheme.typography.titleMedium
               )
            }
            ElevatedButton(onClick = {
               logout()
               navController.navigate(Destination.Login.route){
                  popUpTo(Destination.Main.route) { inclusive = true }
               }
            }) {

               Text(
                  text = stringResource(R.string.logout_btn_text),
                  style = MaterialTheme.typography.titleMedium
               )
            }
         }
      }
   }
}


@Preview(name = "Confirm Logout")
@Composable
fun ConfirmLogoutDialogPreview() {
   PreviewContent {
      ConfirmLogoutContent {

      }
   }

}

@Preview(name = "Confirm Logout dark")
@Composable
fun ConfirmLogoutDialogDarkPreview() {
   PreviewContent(darkTheme = true) {
      ConfirmLogoutContent {

      }
   }

}