package com.gidcode.spendwise.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.LoginDomainModel
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val repository: AuthRepository,
   private val dataStore: SpendWiseDataStore
): ViewModel() {

//   var authToken by mutableStateOf<Resource<AccessTokenDomainModel>>(Resource.Loading)
//      private set

   private val _uiState = MutableStateFlow(UIState())
   val uiState: StateFlow<UIState> = _uiState.asStateFlow()

   fun handleEvent(authenticationEvent: UIEvents) {
      when (authenticationEvent) {
         is UIEvents.LoginUser -> loginUser(authenticationEvent.data)
         is UIEvents.CreateUser -> createUser(authenticationEvent.data)
      }
   }

   init {
      getAccessToken()
   }


   private fun loginUser(data: LoginDomainModel){
      viewModelScope.launch {
//         authToken = Resource.Loading
         _uiState.value = UIState(isLoading = true)
         val result = repository.login(data)
         println("after result")
         result.fold(
            { failure ->
//               authToken = Resource.Error(failure)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
//               authToken = Resource.Success(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, hasAuthToken = true)
               }
            }
         )
      }
   }

   private fun createUser(data: CreateAccountDomainModel){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.createUser(data)
         println("after result")
         result.fold(
            { failure ->
//               authToken = Resource.Error(failure)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
//               authToken = Resource.Success(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, hasAuthToken = true)
               }
            }
         )
      }
   }


   fun getAccessToken() {

       viewModelScope.launch {
          dataStore.getAccessToken().collectLatest { value->
             println("getting token again $value")
             _uiState.value = UIState(hasAuthToken = value != null)
          }
       }
   }
}

data class UIState(
   val isLoading: Boolean = false,
   val hasAuthToken: Boolean = false,
   val error: Exception? = null
)

sealed class UIEvents {
   class LoginUser(val data: LoginDomainModel) : UIEvents()

   class CreateUser(val data: CreateAccountDomainModel) : UIEvents()

}