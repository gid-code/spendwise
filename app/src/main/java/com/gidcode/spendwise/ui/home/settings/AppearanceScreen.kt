package com.gidcode.spendwise.ui.home.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gidcode.spendwise.ui.common.PreviewContent

@Composable
fun AppearanceScreen() {
   AppearanceContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceContent() {
   Scaffold(
      topBar = {
         TopAppBar(title = {
            Text("Appearance")
         })
      }
   ) {innerPadding->
      Column(
         modifier = Modifier.padding(innerPadding)
      ) {
      }
   }
}

@Preview(name = "Appearance")
@Composable
fun AppearanceScreenPreview() {
   PreviewContent {
      AppearanceContent()
   }
}

@Preview(name = "Appearance Dark")
@Composable
fun AppearanceScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      AppearanceContent()
   }
}