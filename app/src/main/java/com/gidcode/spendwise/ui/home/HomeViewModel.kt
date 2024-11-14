package com.gidcode.spendwise.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.data.datasource.local.SpendWiseDataStore
import com.gidcode.spendwise.data.network.SharedAuthState
import com.gidcode.spendwise.domain.model.AddIncomeDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.util.toStringAsFixed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: HomeRepository,
   private val dataStore: SpendWiseDataStore
): ViewModel() {

   private val _uiState = MutableStateFlow(UIState())
   private var _token: String? = null
   private var _isAuthorized = true
   val uiState: StateFlow<UIState> = _uiState.asStateFlow()

   fun handleEvent(homeEvents: UIEvents) {
      when (homeEvents) {
         is UIEvents.AddIncome -> addIncome(homeEvents.data)
      }
   }

   init {

      // Observe unauthorized state
      viewModelScope.launch {
         println("observing auth state")
         SharedAuthState.isUnauthorized.collectLatest { isUnauthorized ->
            _isAuthorized = !isUnauthorized
            if (isUnauthorized) {
               println("calling onUnauth")
               onUnauthorized()
            }
         }
      }

      viewModelScope.launch {
         dataStore.getAccessToken().collectLatest { value->
            _token = value
            fetchAll()
         }
      }
   }

   private fun income(){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.incomes(_token!!)
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure, addIncome = null)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, incomeList = data, addIncome = null)
               }
            }
         )
      }
   }

   private fun expenditure(){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.expenses(_token!!)
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, expenseList = data)
               }
            }
         )
      }
   }

   private fun fetchAll(){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         if (_token == null){
            _uiState.value = UIState(isLoading = false, error = Exception.UnAuthorizedException())
            return@launch
         }
         val result1 = repository.incomes(_token!!)
         val result2 = repository.expenses(_token!!)

         result1.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
               result2.fold(
                  { failure ->
                     _uiState.update { currentState ->
                        currentState.copy(isLoading = false, error = failure)
                     }
                  },
                  {data2 ->
                     _uiState.update { currentState ->
                        currentState.copy(isLoading = false, incomeList = data, expenseList = data2)
                     }
                  }
               )
            }
         )
      }
   }

   private fun addIncome(data: AddIncomeDomainModel) {
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         if (_token == null){
            _uiState.value = UIState(isLoading = false, error = Exception.UnAuthorizedException())
            return@launch
         }
         val result = repository.addIncome(_token!!,data)
         result.fold(
            {failure->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            {data->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, addIncome = data)
               }
               income()
            }
         )
      }
   }

   private fun onUnauthorized() {
      viewModelScope.launch {
         dataStore.clearData()
//         delay(200)
//         println("error to navigate")
//         _uiState.value = UIState(error = Exception.UnAuthorizedException())
      }
   }

}

data class UIState(
   val isLoading: Boolean = false,
   val error: Exception? = null,
   val isAuthorized: Boolean = true,
   val incomeList: List<IncomeItemDomainModel> = emptyList(),
   val expenseList: List<ExpenseItemDomainModel> = emptyList(),
   val totalIncome: Double = incomeList.fold(0.0) { sum, item -> sum + item.amount },
   val totalExpense: Double = expenseList.fold(0.0){ sum, item -> sum + item.estimatedAmount},
   val balance: String = (totalIncome - totalExpense).toStringAsFixed(),
   val addIncome: String? = null
)

sealed class UIEvents {
   class AddIncome(val data: AddIncomeDomainModel) : UIEvents()
}