package com.gidcode.spendwise.ui.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.data.di.usecasefactory.AuthUseCaseFactory
import com.gidcode.spendwise.data.di.usecasefactory.UserUseCaseFactory
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.domain.repository.AuthRepository
import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.util.ThemeMode
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
   private val repository: SettingsRepository,
   private val authUseCaseFactory: AuthUseCaseFactory,
   private val userUseCaseFactory: UserUseCaseFactory
): ViewModel() {
   private val _themeMode =  MutableStateFlow(ThemeMode.SYSTEM)
   private val _user = MutableStateFlow(User("John Doe","johndoe@gmail.com"))
   private val _isBiometricEnabled = MutableStateFlow(false)
   val themeMode: StateFlow<ThemeMode> = _themeMode
   val user: StateFlow<User> = _user
   val isBiometricEnabled: StateFlow<Boolean> = _isBiometricEnabled

   init {
      viewModelScope.launch {
         repository.getThemeMode().collect { value ->
            _themeMode.value = if (value == null){
               ThemeMode.SYSTEM
            }else {
               ThemeMode.valueOf(value.uppercase())
            }
         }
      }

      viewModelScope.launch {
         repository.getBiometricEnabled().collect { value ->
            _isBiometricEnabled.value = value
         }
      }

      user()
   }

   fun setThemeMode(mode: ThemeMode){
      viewModelScope.launch {
         repository.saveThemeMode(mode)
      }
   }

   fun toggleBiometric(){
      viewModelScope.launch {
         repository.toggleBiometric()
      }
   }

   private fun user(){
      viewModelScope.launch {
         userUseCaseFactory.getUserUseCase.invoke().collect{value ->
            if (value == null){
               getRemoteUser()
            }else {
               val json: Json = Json
               val currentUser = json.decodeFromString<User>(value)
               _user.value = currentUser
            }

         }
      }
   }

   private fun getRemoteUser() {
      viewModelScope.launch {
         authUseCaseFactory.getAccessTokenUseCase.invoke().collect{token->
            if (token != null){
               userUseCaseFactory.getUserIdUseCase.invoke().collect{uuid->
                  if (uuid != null){
                     val result = userUseCaseFactory.getRemoteUserUseCase.invoke(uuid)
                     result.getOrNull()?.let { user->
                        userUseCaseFactory.storeUserUseCase.invoke(user)
                        _user.value = user
                     }
                  }
               }
            }
         }
      }
   }

   fun logout() {
      viewModelScope.launch {
         authUseCaseFactory.logoutUseCase.invoke()
      }
   }
}