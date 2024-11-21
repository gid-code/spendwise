package com.gidcode.spendwise.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.ui.common.ErrorViewWithoutButton
import com.gidcode.spendwise.ui.common.ListTile
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.theme.otherGradientColor
import com.gidcode.spendwise.util.Orientation
import com.gidcode.spendwise.util.addMoveAnimation
import com.gidcode.spendwise.util.toStringAsFixed
import com.google.accompanist.insets.navigationBarsWithImePadding


@Composable
fun HomeScreen() {
   val homeViewModel = ViewModelProvider.homeViewModel
   val uiState by homeViewModel.uiState.collectAsState()
   HomeScreenContent(
      uiState
   )
}


@Composable
fun HomeScreenContent(
   uiState: UIState
) {
   val navController = Navigator.current
   var showError by remember { mutableStateOf(false) }

   LaunchedEffect(key1 = uiState) {
      if (!uiState.isAuthorized) {
         navController.navigate(Destination.OnBoarding.route) {
            popUpTo(Destination.Main.route) { inclusive = true }
         }
      }else if (uiState.error != null){
         showError = true
      }
   }

   Surface(
      modifier = Modifier.fillMaxSize()
   ){
      Box(modifier = Modifier.fillMaxSize()) {
         Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.28f)
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
               .padding(16.dp)
               .fillMaxSize()
         ) {
            Spacer(modifier = Modifier.fillMaxHeight(0.05f))
            Row (
               modifier = Modifier.fillMaxWidth(),
               horizontalArrangement = Arrangement.SpaceBetween
            ) {
               Column {
                  Text(text = "Welcome,",
                     style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                     )
                  )
                  Spacer(modifier = Modifier.height(2.dp))
                  Text(text = "John !",
                     style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                     )
                  )
               }
               IconButton(onClick = { /*TODO*/ }) {
                  Icon(
                     imageVector = Icons.Default.Notifications,
                     contentDescription = "notification",
                     tint = MaterialTheme.colorScheme.onPrimary
                  )
               }
            }
            Spacer(modifier = Modifier.height(24.dp))
            SummaryCard(
               balance = uiState.balance,
               income = uiState.totalIncome,
               expense = uiState.totalExpense,
               visible = true
            )
            Box(modifier = Modifier.fillMaxSize()){
               Column(modifier =
                  Modifier
                     .verticalScroll(rememberScrollState())
               ) {
                  Spacer(modifier = Modifier.height(24.dp))
                  IncomeExpensesChart(income = uiState.totalIncome, expenses = uiState.totalExpense)
                  Spacer(modifier = Modifier.height(24.dp))
                  RevenueHistory(income = uiState.incomeList, openDialog = {navController.navigate(Destination.AddIncome.route)})
                  Spacer(modifier = Modifier.height(74.dp))
               }
            }
         }
//         Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.Bottom
//         ) {
//
//            if (showError){
//               uiState.error?.message?.let {
//                  ErrorViewWithoutButton(text = it, visible = showError) {
//                     showError = false
//                  }
//               }
//            }
//
//            Spacer(modifier = Modifier.fillMaxHeight(0.15f))
//         }
         Box(
            modifier = Modifier
               .align(Alignment.BottomCenter)
               .padding(bottom = 100.dp)
               .navigationBarsWithImePadding()
         ) {
            uiState.error?.message?.let {
               ErrorViewWithoutButton(text = it, visible = showError) {
                  showError = false
               }
            }
         }
         if (uiState.isLoading && uiState.incomeList.isEmpty() && uiState.expenseList.isEmpty()) {
            Box(
               modifier = Modifier
                  .fillMaxSize()
                  .background(color = Color.Black.copy(alpha = 0.5f))
            ){
               CircularProgressIndicator(
                  modifier = Modifier.align(alignment = Alignment.Center)
               )
            }
         }
      }
   }

}

@Composable
fun RevenueHistory(income: List<IncomeItemDomainModel>, openDialog: () -> Unit) {
   Column {
      Row(
         Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically
      ) {
         Text(text = "Revenue History", style = MaterialTheme.typography.titleLarge)
         ElevatedButton(
            onClick = openDialog,
            colors = ButtonColors(
               containerColor = MaterialTheme.colorScheme.primary,
               contentColor = MaterialTheme.colorScheme.onPrimary,
               disabledContainerColor = Color.Gray,
               disabledContentColor = Color.Gray
            )
         ) {
            Row(
               verticalAlignment = Alignment.CenterVertically
            ) {
               Icon(imageVector = Icons.Filled.Add, contentDescription = "add button")
               Spacer(modifier = Modifier.width(5.dp))
               Text(text = "Add New", style = MaterialTheme.typography.bodyMedium)
            }
         }
      }
      Spacer(modifier = Modifier.height(8.dp))
      if (income.isEmpty()) {
         Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
         ) {
            Icon(
               modifier = Modifier.size(64.dp),
               imageVector = Icons.Default.AccountBalanceWallet,
               contentDescription = "empty wallet",
               tint = MaterialTheme.colorScheme.primary.copy(
                  alpha = 0.5f
               )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
               text = "No revenue history yet",
               style = MaterialTheme.typography.titleMedium.copy(
                  color = MaterialTheme.colorScheme.onSurface.copy(
                     alpha = 0.7f
                  )
               )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
               text = "Tap Add New to record your first income",
               style = MaterialTheme.typography.bodyMedium.copy(
                  color = MaterialTheme.colorScheme.onSurface.copy(
                     alpha = 0.5f
                  )
               )
            )
         }
      } else {
         income.forEach { income ->
            key(income.id) {
               ListTile(
                  title = income.nameOfRevenue,
                  trailing = "GHS ${income.amount.toDouble().toStringAsFixed()}"
               )
            }
         }
      }
   }
}

@Composable
fun IncomeExpensesChart(income: Double,expenses: Double) {
   val maxValue = listOf(income,expenses).maxOrNull() ?: 0.0
   val incomeValue = ((income * 170) / maxValue).toInt()
   val expensesValue = ((expenses * 170) / maxValue).toInt()

   Card {
      Column(
         modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
         horizontalAlignment = Alignment.Start
      ) {
         Text(
            text = "Income Vs Expenses",
            style = MaterialTheme.typography.titleLarge
         )
         Spacer(modifier = Modifier.height(16.dp))
         Box(
            modifier = Modifier
               .fillMaxWidth()
               .height(200.dp)
         ) {
            Column(
               modifier = Modifier.align(Alignment.BottomStart)
            ) {
               //y-axis
               Row {
                  if (maxValue != 0.0) {
                     Box(
                        modifier = Modifier
                           .width(20.dp)
                           .height(175.dp)
                     ) {
                        Text(
                           text = maxValue.toStringAsFixed(),
                           maxLines = 1
                        )
                     }
                  }
                  Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceAround,
                     verticalAlignment = Alignment.Bottom
                  ) {
                     Box(
                        modifier = Modifier
                           .height(incomeValue.dp)
                           .width(20.dp)
                           .background(color = MaterialTheme.colorScheme.primary)
                     )
                     Box(
                        modifier = Modifier
                           .height(expensesValue.dp)
                           .width(20.dp)
                           .background(color = MaterialTheme.colorScheme.secondary)
                     )
                  }

               }
               //x-axis
               Row {
                  Text(text = "0")
                  Spacer(modifier = Modifier.width(20.dp))
                  Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceAround
                  ) {
                     Text(text = "Income")
                     Text(text = "Expenses")
                  }

               }

            }
         }
      }
   }
}

@Composable
fun SummaryCard(balance: String, income: Double, expense: Double, visible : Boolean = true) {

   Card (
      modifier = Modifier.addMoveAnimation(
         orientation = Orientation.Vertical,
         from = 0.dp,
         to = 10.dp,
         duration = 200
      ),
      colors = CardColors(
         containerColor = MaterialTheme.colorScheme.primary,
         contentColor = MaterialTheme.colorScheme.primary,
         disabledContentColor = Color.Gray,
         disabledContainerColor = Color.Gray
      ),
      elevation = CardDefaults.elevatedCardElevation(
         defaultElevation = 8.dp
      )
   ) {
      Column (
         modifier = Modifier.padding(16.dp)
      ) {
         Text(text = "Total Balance",
            style = MaterialTheme.typography.titleSmall.copy(
               color = MaterialTheme.colorScheme.onPrimary
            )
         )
         Spacer(modifier = Modifier.height(8.dp))
         Text(text = "GHS $balance",
            style = MaterialTheme.typography.headlineMedium.copy(
               color = MaterialTheme.colorScheme.onPrimary,
               fontWeight = FontWeight.Bold
            )
         )
         Spacer(modifier = Modifier.height(16.dp))
         Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
         ){
            MiniSummary(title="Income", amount="GHS ${income.toStringAsFixed()}", color = MaterialTheme.colorScheme.onPrimary)
            MiniSummary(title="Expenses", amount="GHS ${expense.toStringAsFixed()}", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f))
         }
      }
   }
}


@Composable
fun MiniSummary(title: String, amount: String, color: Color) {
   Column {
      Row (
         verticalAlignment = Alignment.CenterVertically
      ) {
         Box(modifier = Modifier
            .background(
               color = MaterialTheme.colorScheme.onPrimary.copy(
                  alpha = 0.2f
               ),
               shape = CircleShape
            )
            .padding(2.dp)
         ){
            Icon(
               imageVector = if (title.lowercase().contains("income"))
            Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
               contentDescription = "arrows",
               tint = MaterialTheme.colorScheme.onPrimary
            )
         }
         Spacer(modifier = Modifier.width(4.dp))
         Text(text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
               color = MaterialTheme.colorScheme.onPrimary.copy(
                  alpha = 0.8f
               )
            ))
      }
      Spacer(modifier = Modifier.height(4.dp))
      Text(text = amount,
         style = MaterialTheme.typography.titleMedium.copy(
            color = color
         )
      )
   }
}

@Preview(name = "Home")
@Composable
fun HomeScreenPreview() {
   PreviewContent {
      HomeScreenContent(
         uiState = UIState(
            incomeList = listOf(
               IncomeItemDomainModel("ddd","Salary",30),
               IncomeItemDomainModel("334","Gig",1000)
            ),
            expenseList = listOf(
               ExpenseItemDomainModel("ddd","Salary",30),

            ),
            isLoading = false,
         )
      )
   }
}

@Preview(name = "Home Dark")
@Composable
fun HomeScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      HomeScreenContent(uiState = UIState(
         isLoading = false,
         totalExpense = 900.0,
         totalIncome = 10000.0
      ))
   }
}