package com.gidcode.spendwise.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.gidcode.spendwise.R
import com.gidcode.spendwise.domain.model.AddIncomeDomainModel
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun AddIncomeDialog() {
   val homeViewModel = ViewModelProvider.homeViewModel
   val uiState by homeViewModel.uiState.collectAsState()
   val uiEvent: (UIEvents) -> Unit = homeViewModel::handleEvent
   AddIncomeContent(
      uiState,
      uiEvent
   )
}

@Composable
fun AddIncomeContent(
   uiState: UIState,
   addIncome: (UIEvents) -> Unit
) {
   val navController = Navigator.current
   var name by remember { mutableStateOf("") }
   var amount by remember { mutableStateOf("") }

   val isFormValid by remember { derivedStateOf {
      name.isNotBlank() && amount.isNotBlank()
   } }

   LaunchedEffect(key1 = uiState) {
      if (uiState.addIncome != null){
         navController.popBackStack()
      }
   }

   Dialog(onDismissRequest = { navController.popBackStack() }) {
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

         Text(text = stringResource(R.string.add_new_revenue_title),
            style = MaterialTheme.typography.titleLarge.copy(
               color = MaterialTheme.colorScheme.onSurface
            )
         )
         Spacer(modifier = Modifier.height(24.dp))

         TextField(
            value = name,
            onValueChange = {name = it},
            label = { Text(stringResource(R.string.revenue_name)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
         )

         Spacer(modifier = Modifier.height(16.dp))

         TextField(
            value = amount,
            onValueChange = {amount = it},
            label = { Text(stringResource(R.string.revenue_amount)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            enabled = !uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
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
               if (isFormValid){
                  addIncome.invoke(UIEvents.AddIncome(AddIncomeDomainModel(name,amount.toDouble())))
               }
            }) {
               if (uiState.isLoading) {
                  CircularProgressIndicator(
                     color = MaterialTheme.colorScheme.onPrimary
                  )
               } else {
                  Text(
                     text = stringResource(R.string.submit_btn_text),
                     style = MaterialTheme.typography.titleMedium
                  )
               }
            }
         }
      }
   }
}


@Preview(name = "AddIncome")
@Composable
fun AddIncomeDialogPreview() {
   PreviewContent {
      AddIncomeContent(
         uiState = UIState(),
         addIncome = {}
      )
   }
}

@Preview(name = "AddIncome Dark")
@Composable
fun AddIncomeDialogDarkPreview() {
   PreviewContent(darkTheme = true) {
      AddIncomeContent(
         uiState = UIState(),
         addIncome = {}
      )
   }
}