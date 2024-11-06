package com.gidcode.spendwise.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gidcode.spendwise.ui.auth.AuthViewModel
import com.gidcode.spendwise.ui.home.HomeViewModel

object ViewModelProvider {
   val authToken: AuthViewModel
      @Composable
      get() = LocalAuthViewModel.current

   val homeViewModel: HomeViewModel
      @Composable
      get() = LocalHomeViewModel.current
//
//   val podcastPlayer: PodcastPlayerViewModel
//      @Composable
//      get() = LocalPodcastPlayerViewModel.current
}

@Composable
fun ProvideMultiViewModel(content: @Composable () -> Unit) {
   val viewModel1: AuthViewModel = viewModel()
   val viewModel2: HomeViewModel = viewModel()
//   val viewModel3: PodcastPlayerViewModel = viewModel()

   CompositionLocalProvider(
      LocalAuthViewModel provides viewModel1,
   ) {
      CompositionLocalProvider(
         LocalHomeViewModel provides viewModel2,
      ) {
//         CompositionLocalProvider(
//            LocalPodcastPlayerViewModel provides viewModel3,
//         ) {
            content()
//         }
      }
   }
}

private val LocalAuthViewModel = staticCompositionLocalOf<AuthViewModel> {
   error("No AuthViewModel provided")
}

private val LocalHomeViewModel = staticCompositionLocalOf<HomeViewModel> {
   error("No HomeViewModel provided")
}