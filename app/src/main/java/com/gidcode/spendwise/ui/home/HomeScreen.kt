package com.gidcode.spendwise.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.theme.otherGradientColor


@Composable
fun HomeScreen() {
   HomeScreenContent()
}


@Composable
fun HomeScreenContent() {
//   val scrollState by rememberScrollableState {
//
//   }
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
         ){}
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
            SummaryCard()
            Box(modifier = Modifier.fillMaxSize()){
               Column(modifier =
                  Modifier
                     .verticalScroll(rememberScrollState())
               ) {
                  Spacer(modifier = Modifier.height(24.dp))
                  IncomeExpensesChart()
                  Spacer(modifier = Modifier.height(24.dp))
                  RevenueHistory(income = listOf())
                  Spacer(modifier = Modifier.height(74.dp))
               }
            }
         }
      }
   }

}

@Composable
fun RevenueHistory(income: List<String>) {
   Column {
      Row (
         Modifier.fillMaxWidth(),
         horizontalArrangement = Arrangement.SpaceBetween,
         verticalAlignment = Alignment.CenterVertically
      ) {
         Text(text = "Revenue History", style= MaterialTheme.typography.titleLarge )
         ElevatedButton(
            onClick = { /*TODO*/ },
            colors = ButtonColors(
               containerColor = MaterialTheme.colorScheme.primary,
               contentColor = MaterialTheme.colorScheme.onPrimary,
               disabledContainerColor = Color.Gray,
               disabledContentColor = Color.Gray
            )
         ) {
            Row (
               verticalAlignment = Alignment.CenterVertically
            ){
               Icon(imageVector = Icons.Filled.Add, contentDescription = "add button")
               Spacer(modifier = Modifier.width(5.dp))
               Text(text = "Add New", style = MaterialTheme.typography.bodyMedium)
            }
         }
      }
      if (income.isEmpty()){
         Column (
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
      }else {

      }
   }
}

@Composable
fun IncomeExpensesChart() {
   Card {
      Column (
         modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
         horizontalAlignment = Alignment.Start
      ) {
         Text(text = "Income Vs Expenses",
            style = MaterialTheme.typography.titleLarge
         )
         Spacer(modifier = Modifier.height(16.dp))
         Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
         ){

         }
      }
   }
}

@Composable
fun SummaryCard() {
   Card (
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
         Text(text = "GHS 100.00",
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
            MiniSummary(title="Income", amount="GHS 20000.00", color = MaterialTheme.colorScheme.onPrimary)
            MiniSummary(title="Expenses", amount="GHS 100.00", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f))
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
      HomeScreenContent()
   }
}

@Preview(name = "Home Dark")
@Composable
fun HomeScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      HomeScreenContent()
   }
}