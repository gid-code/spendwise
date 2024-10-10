package com.gidcode.spendwise.ui.common

import androidx.compose.runtime.Composable
import com.gidcode.spendwise.ui.navigation.ProvideNavHostController
import com.gidcode.spendwise.ui.theme.AppTheme

@Composable
fun PreviewContent(
   darkTheme: Boolean = false,
   content: @Composable () -> Unit
) {
   AppTheme(darkTheme = darkTheme) {

      ProvideNavHostController {
         content()
      }
   }
}