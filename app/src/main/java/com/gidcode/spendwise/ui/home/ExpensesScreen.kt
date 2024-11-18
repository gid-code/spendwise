package com.gidcode.spendwise.ui.home

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.ui.common.ListTile
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.theme.otherGradientColor
import com.gidcode.spendwise.util.toStringAsFixed

@Composable
fun ExpensesScreen() {
   val homeViewModel = ViewModelProvider.homeViewModel
   val uiState by homeViewModel.uiState.collectAsState()
   ExpensesScreenContent(
      uiState
   )
}

@Composable
fun ExpensesScreenContent(uiState: UIState) {


//   Scaffold(
//      floatingActionButton = {
//         AddExpensesActionButton(
//            openDialog = { navController.navigate(Destination.AddExpenses.route) }
//         )
//      }
//   ) {padding->padding
   Surface(
      modifier = Modifier
         .fillMaxSize()
   ) {
      Box(modifier = Modifier.fillMaxSize()) {
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
         ){
            Text(text = "My Expenses",
               style = MaterialTheme.typography.displaySmall.copy(
                  color = MaterialTheme.colorScheme.onPrimary
               ),
               modifier = Modifier.align(alignment = Alignment.Center)
            )
         }

         Column(
            modifier = Modifier
               .padding(16.dp)
               .fillMaxSize()
         ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.26f))
            if (uiState.expenseList.isEmpty()) {
               Column(
                  modifier = Modifier.fillMaxSize(),
                  horizontalAlignment = Alignment.CenterHorizontally,
                  verticalArrangement = Arrangement.Center
               ) {
                  Icon(
                     imageVector = Icons.AutoMirrored.Filled.ReceiptLong,
                     contentDescription = "receipt icon",
                     tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                     modifier = Modifier.size(64.dp)
                  )
                  Spacer(modifier = Modifier.height(16.dp))
                  Text(
                     text = "No expenses found",
                     style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
                     )
                  )
                  Spacer(modifier = Modifier.height(8.dp))
                  Text(
                     text = "Tap the + button to add an expense",
                     style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                     )
                  )
               }
            }else {
               uiState.groupExpensesByCategory().forEach { pair ->
                  Column {
                     Text(text = pair.first,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(
                           vertical = 16.dp
                        )
                     )
                     pair.second.forEach {expense->
                        ExpensesItemTitle(
                           expense
                        )
                     }
                     HorizontalDivider()
                  }
               }
            }
         }
      }

   }
//   }
}

@Composable
fun ExpensesItemTitle(expense: ExpenseItemDomainModel) {
   Box(
      modifier = Modifier
         .fillMaxWidth()
         .padding(bottom = 4.dp)
   ) {
      Row (
         modifier = Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically
      ){
         Row(
            verticalAlignment = Alignment.CenterVertically
         ){
            Box(modifier = Modifier.size(40.dp)
               .background(color = Color.Red.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
            )
            {
               Icon(
                  imageVector = Icons.Default.Cake,
                  contentDescription = "cake",
                  modifier = Modifier.align(Alignment.Center)
               )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = expense.nameOfItem,
               style = MaterialTheme.typography.titleMedium
            )
         }
         Text(text = "GHS ${expense.estimatedAmount.toDouble().toStringAsFixed()}",
            style = MaterialTheme.typography.titleMedium.copy(
               color = MaterialTheme.colorScheme.primary,
               fontWeight = FontWeight.Bold
            )
         )
      }
   }
}

@Composable
fun AddExpensesActionButton(openDialog: ()-> Unit) {
   FloatingActionButton(onClick = openDialog) {
      Icon(imageVector = Icons.Default.Add, contentDescription = "fab icon")
   }
}

@Preview(name = "Expenses")
@Composable
fun ExpensesScreenPreview() {
   PreviewContent {
      ExpensesScreenContent(UIState(
         expenseList = listOf(
            ExpenseItemDomainModel("Education","Books",24),
            ExpenseItemDomainModel("Education","Tuition",24),
            ExpenseItemDomainModel("Accommodation","Rent",200)
         )
      ))
   }
}

@Preview(name = "Expenses Dark")
@Composable
fun ExpensesScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      ExpensesScreenContent(UIState())
   }
}