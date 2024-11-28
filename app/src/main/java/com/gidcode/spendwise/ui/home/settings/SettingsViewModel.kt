package com.gidcode.spendwise.ui.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
   private val authRepository: AuthRepository
): ViewModel() {
   private val _themeMode =  MutableStateFlow(ThemeMode.SYSTEM)
   private val _user = MutableStateFlow(User("John Doe","johndoe@gmail.com"))
   val themeMode: StateFlow<ThemeMode> = _themeMode
   val user: StateFlow<User> = _user

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

      user()
   }

   fun setThemeMode(mode: ThemeMode){
      viewModelScope.launch {
         repository.saveThemeMode(mode)
      }
   }

   private fun user(){
      viewModelScope.launch {
         repository.getUser().collect{value ->
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
         authRepository.getAuthToken().collect{token->
            if (token != null){
               repository.getUserId().collect{uuid->
                  if (uuid != null){
                     val result = repository.getRemoteUser(uuid,token)
                     result.getOrNull()?.let { user->
                        repository.saveUser(user)
                        _user.value = user
                     }
                  }
               }
            }
         }
      }
   }
}