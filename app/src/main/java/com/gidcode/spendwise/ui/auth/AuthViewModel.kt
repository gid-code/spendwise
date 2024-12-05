package com.gidcode.spendwise.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.di.usecasefactory.UserUseCaseFactory
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.LoginDomainModel
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val repository: AuthRepository,
   private val userUseCaseFactory: UserUseCaseFactory
): ViewModel() {

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

      viewModelScope.launch {
         userUseCaseFactory.getBiometricEnabledUseCase.invoke().collect { value->
            _uiState.update { currentState -> currentState.copy(isBiometricEnabled = value) }
         }
      }
   }


   private fun loginUser(data: LoginDomainModel){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.login(data)
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
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
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, hasAuthToken = true)
               }
            }
         )
      }
   }


   private fun getAccessToken() {

       viewModelScope.launch {
          repository.getAuthToken().collect { value->
             _uiState.update { currentState -> currentState.copy(hasAuthToken = value != null) }
          }
       }
   }
}

data class UIState(
   val isLoading: Boolean = false,
   val hasAuthToken: Boolean = false,
   val error: Exception? = null,
   val isBiometricEnabled: Boolean = false
)

sealed class UIEvents {
   class LoginUser(val data: LoginDomainModel) : UIEvents()

   class CreateUser(val data: CreateAccountDomainModel) : UIEvents()

}