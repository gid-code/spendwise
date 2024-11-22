package com.gidcode.spendwise.ui.common

import androidx.compose.runtime.Composable
import com.gidcode.spendwise.ui.navigation.ProvideNavHostController
import com.gidcode.spendwise.ui.theme.AppTheme
import com.gidcode.spendwise.util.ThemeMode

@Composable
fun PreviewContent(
   darkTheme: Boolean = false,
   content: @Composable () -> Unit
) {
   AppTheme(themeMode = if (darkTheme) ThemeMode.DARK else ThemeMode.LIGHT) {

      ProvideNavHostController {
         content()
      }
   }
}