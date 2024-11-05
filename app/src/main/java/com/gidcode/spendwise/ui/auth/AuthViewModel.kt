package com.gidcode.spendwise.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.domain.model.AccessTokenDomainModel
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.domain.model.LoginDomainModel
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val repository: AuthRepository
): ViewModel() {

   var authToken by mutableStateOf<Resource<AccessTokenDomainModel>>(Resource.Loading)
      private set

//   var uiState by


   fun loginUser(data: LoginDomainModel){
      viewModelScope.launch {
         authToken = Resource.Loading
         val result = repository.login(data)
         println("after result")
         result.fold(
            { failure ->
               authToken = Resource.Error(failure)
            },
            { data ->
               println(data)
               authToken = Resource.Success(data)
            }
         )
      }
   }

   fun createUser(data: CreateAccountDomainModel){
      viewModelScope.launch {
         authToken = Resource.Loading
         val result = repository.createUser(data)
         println("after result")
         result.fold(
            { failure ->
               authToken = Resource.Error(failure)
            },
            { data ->
               println(data)
               authToken = Resource.Success(data)
            }
         )
      }
   }
}