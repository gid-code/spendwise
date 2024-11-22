package com.gidcode.spendwise.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gidcode.spendwise.ui.auth.AuthViewModel
import com.gidcode.spendwise.ui.home.HomeViewModel
import com.gidcode.spendwise.ui.home.settings.SettingsViewModel

object ViewModelProvider {
   val authToken: AuthViewModel
      @Composable
      get() = LocalAuthViewModel.current

   val homeViewModel: HomeViewModel
      @Composable
      get() = LocalHomeViewModel.current

   val settingsViewModel: SettingsViewModel
      @Composable
      get() = LocalSettingsViewModel.current
}

@Composable
fun ProvideMultiViewModel(content: @Composable () -> Unit) {
   val viewModel1: SettingsViewModel = viewModel()
   val viewModel2: AuthViewModel = viewModel()
   val viewModel3: HomeViewModel = viewModel()

   CompositionLocalProvider(
      LocalSettingsViewModel provides viewModel1,
   ) {
      CompositionLocalProvider(
         LocalAuthViewModel provides viewModel2,
      ) {
         CompositionLocalProvider(
            LocalHomeViewModel provides viewModel3,
         ) {
            content()
         }
      }
   }
}

private val LocalSettingsViewModel = staticCompositionLocalOf<SettingsViewModel> {
   error("No SettingsViewModel provided")
}

private val LocalAuthViewModel = staticCompositionLocalOf<AuthViewModel> {
   error("No AuthViewModel provided")
}

private val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
   error("No HomeViewModel provided")
}