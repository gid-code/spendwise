package com.gidcode.spendwise.ui.home.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.domain.repository.SettingsRepository
import com.gidcode.spendwise.util.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
   private val repository: SettingsRepository
): ViewModel() {
   private val _themeMode =  MutableStateFlow(ThemeMode.SYSTEM)
   val themeMode: StateFlow<ThemeMode> = _themeMode

   init {
      viewModelScope.launch {
         repository.getThemeMode().collect { value ->
            println("ThemeMode: $value")
            _themeMode.value = if (value == null){
               ThemeMode.SYSTEM
            }else {
               ThemeMode.valueOf(value.uppercase())
            }
         }
      }
   }

   fun setThemeMode(mode: ThemeMode){
      viewModelScope.launch {
         repository.saveThemeMode(mode)
      }
   }
}