package com.gidcode.spendwise.ui.home.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Navigator
import com.gidcode.spendwise.util.ThemeMode

@Composable
fun AppearanceScreen() {
   val settingsViewModel = ViewModelProvider.settingsViewModel
   val theme by settingsViewModel.themeMode.collectAsState()
   AppearanceContent(
      theme
   ) {themeMode ->
      settingsViewModel.setThemeMode(themeMode)
   }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceContent(
   theme: ThemeMode,
   setTheme: (ThemeMode) -> Unit) {
   val navController = Navigator.current
   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text("Appearance")
            },
            navigationIcon = {
               IconButton(
                  onClick = {
                     navController.popBackStack()
                  }
               ) {
                  Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "")
               }
            }
         )
      }
   ) {innerPadding->
      Column(
         modifier = Modifier.padding(innerPadding)
      ) {
         RadioButtonGroup(
            options = ThemeMode.entries.toList(),
            selectedOption = theme,
            onOptionSelected = setTheme
         )
      }
   }
}

@Composable
fun RadioButtonGroup(
   options: List<ThemeMode>,
   selectedOption: ThemeMode,
   onOptionSelected: (ThemeMode) -> Unit
) {
   val shape = RoundedCornerShape(20)
   options.forEach { themeMode ->
      val isSelected = selectedOption == themeMode
      Box(
         modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clip(shape)
            .background(
               color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.secondaryContainer.copy(
                  alpha = 0.8f
               ),
            )
            .border(
               width = 1.dp,
               color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary.copy(
                  alpha = 0.4f
               ),
               shape = shape,
            )
            .clickable { onOptionSelected(themeMode) }
      ) {
         Row(
            modifier = Modifier
               .padding(all = 8.dp)
               .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
         ) {
            RadioButton(
               selected = isSelected,
               onClick = {

               }
            )
            Text(text = themeMode.toString().capitalize())
         }
      }
   }
}

@Preview(name = "Appearance")
@Composable
fun AppearanceScreenPreview() {
   PreviewContent {
      AppearanceContent(
         ThemeMode.SYSTEM
      ) { }
   }
}

@Preview(name = "Appearance Dark")
@Composable
fun AppearanceScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      AppearanceContent(ThemeMode.DARK) {}
   }
}