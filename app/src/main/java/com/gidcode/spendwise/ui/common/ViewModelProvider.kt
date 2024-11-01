package com.gidcode.spendwise.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gidcode.spendwise.ui.auth.AuthViewModel

object ViewModelProvider {
   val authToken: AuthViewModel
      @Composable
      get() = LocalAuthViewModel.current

//   val podcastDetail: PodcastDetailViewModel
//      @Composable
//      get() = LocalPodcastDetailViewModel.current
//
//   val podcastPlayer: PodcastPlayerViewModel
//      @Composable
//      get() = LocalPodcastPlayerViewModel.current
}

@Composable
fun ProvideMultiViewModel(content: @Composable () -> Unit) {
   val viewModel1: AuthViewModel = viewModel()
//   val viewModel2: PodcastDetailViewModel = viewModel()
//   val viewModel3: PodcastPlayerViewModel = viewModel()

   CompositionLocalProvider(
      LocalAuthViewModel provides viewModel1,
   ) {
//      CompositionLocalProvider(
//         LocalPodcastDetailViewModel provides viewModel2,
//      ) {
//         CompositionLocalProvider(
//            LocalPodcastPlayerViewModel provides viewModel3,
//         ) {
            content()
//         }
//      }
   }
}

private val LocalAuthViewModel = staticCompositionLocalOf<AuthViewModel> {
   error("No AuthViewModel provided")
}