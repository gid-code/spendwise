package com.gidcode.spendwise.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.util.toStringAsFixed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: HomeRepository,
): ViewModel() {

   private val _uiState = MutableStateFlow(UIState())
   val uiState: StateFlow<UIState> = _uiState.asStateFlow()

   init {
      income()
      expenditure()
   }

   private fun income(){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.incomes()
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, incomeList = data)
               }
            }
         )
      }
   }

   private fun expenditure(){
      viewModelScope.launch {
         _uiState.value = UIState(isLoading = true)
         val result = repository.expenses()
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

}

data class UIState(
   val isLoading: Boolean = false,
   val error: Exception? = null,
   val incomeList: List<IncomeItemDomainModel> = emptyList(),
   val expenseList: List<ExpenseItemDomainModel> = emptyList(),
   val totalIncome: Double = incomeList.fold(0.0) { sum, item -> sum + item.amount },
   val totalExpense: Double = expenseList.fold(0.0){ sum, item -> sum + item.estimatedAmount},
   val balance: String = (totalIncome - totalExpense).toStringAsFixed(),
)