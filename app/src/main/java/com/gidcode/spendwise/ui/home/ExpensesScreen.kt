package com.gidcode.spendwise.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.ui.theme.otherGradientColor

@Composable
fun ExpensesScreen() {
   ExpensesScreenContent()
}

@Composable
fun ExpensesScreenContent(){
   val navController = Navigator.current

   Scaffold(
      floatingActionButton = {
         AddExpensesActionButton(
            openDialog = { navController.navigate(Destination.AddExpenses.route) }
         )
      }
   ) {padding->
      Surface(
         modifier = Modifier
            .fillMaxSize()
            .padding(padding)
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
         }
         LazyColumn {
            item {
               Spacer(modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max))
            }

         }

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
      ExpensesScreenContent()
   }
}

@Preview(name = "Expenses Dark")
@Composable
fun ExpensesScreenScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      ExpensesScreenContent()
   }
}