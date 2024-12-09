package com.gidcode.spendwise.ui.home

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gidcode.spendwise.data.network.interceptor.SharedAuthState
import com.gidcode.spendwise.data.di.usecasefactory.AuthUseCaseFactory
import com.gidcode.spendwise.data.di.usecasefactory.UserUseCaseFactory
import com.gidcode.spendwise.domain.model.AddExpenseDomainModel
import com.gidcode.spendwise.domain.model.AddIncomeDomainModel
import com.gidcode.spendwise.domain.model.Exception
import com.gidcode.spendwise.domain.model.ExpenseItemDomainModel
import com.gidcode.spendwise.domain.model.IncomeItemDomainModel
import com.gidcode.spendwise.domain.model.User
import com.gidcode.spendwise.domain.repository.HomeRepository
import com.gidcode.spendwise.util.toStringAsFixed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
   private val repository: HomeRepository,
   private val userUseCaseFactory: UserUseCaseFactory,
   private val authUseCaseFactory: AuthUseCaseFactory
): ViewModel() {

   private val _uiState = MutableStateFlow(UIState())
   private var _token: String? = null
   private var _isAuthorized = true
   val uiState: StateFlow<UIState> = _uiState.asStateFlow()

   fun handleEvent(homeEvents: UIEvents) {
      when (homeEvents) {
         is UIEvents.AddIncome -> addIncome(homeEvents.data)
         is UIEvents.AddExpense -> addExpense(homeEvents.data)
      }
   }

   init {

      // Observe unauthorized state
      viewModelScope.launch {
         SharedAuthState.isUnauthorized.collectLatest { isUnauthorized ->
            _isAuthorized = !isUnauthorized
            if (isUnauthorized) {
//               println("calling onUnauth")
               onUnauthorized()
            }
         }
      }

      viewModelScope.launch {
         authUseCaseFactory.getAccessTokenUseCase.invoke().collectLatest { value->
            _token = value
            value?.let { fetchAll() }
         }
      }

      user()
   }

   private fun income(){
      viewModelScope.launch {
         _uiState.update { it.copy(isLoading = true) }
         val result = repository.incomes()
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure, addIncome = null)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  val totalIncome = data.fold(0.0) { sum, item -> sum + item.amount }
                  currentState.copy(
                     isLoading = false,
                     incomeList = data,
                     totalIncome = totalIncome,
                     balance = (totalIncome - currentState.totalExpense).toStringAsFixed(),
                     addIncome = null
                  )
               }
            }
         )
      }
   }

   private fun expenditure(){
      viewModelScope.launch {
         _uiState.update { it.copy(isLoading = true) }
         val result = repository.expenses()
         result.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure, addExpense = null)
               }
            },
            { data ->
               println(data)
               _uiState.update { currentState ->
                  val totalExpense = data.fold(0.0){ sum, item -> sum + item.estimatedAmount}
                  currentState.copy(
                     isLoading = false,
                     expenseList = data,
                     totalExpense = totalExpense,
                     balance = (currentState.totalIncome - totalExpense).toStringAsFixed(),
                     addExpense = null
                  )
               }
            }
         )
      }
   }

   private fun fetchAll(){
      viewModelScope.launch {
         _uiState.update { it.copy(isLoading = true) }
         val result1 = repository.incomes()

         result1.fold(
            { failure ->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            { data ->
               println(data)
               viewModelScope.launch {
                  val result2 = repository.expenses()
                  result2.fold(
                     { failure ->
                        _uiState.update { currentState ->
                           currentState.copy(isLoading = false, error = failure)
                        }
                     },
                     {data2 ->
                        _uiState.update { currentState ->
                           val totalIncome = data.fold(0.0) { sum, item -> sum + item.amount }
                           val totalExpense = data2.fold(0.0){ sum, item -> sum + item.estimatedAmount}
                           currentState.copy(
                              isLoading = false,
                              incomeList = data,
                              expenseList = data2,
                              totalIncome = totalIncome,
                              totalExpense = totalExpense,
                              balance = (totalIncome - totalExpense).toStringAsFixed()
                           )
                        }
                     }
                  )
               }
            }
         )
      }
   }

   private fun addIncome(data: AddIncomeDomainModel) {
      viewModelScope.launch {
         _uiState.update { it.copy(isLoading = true) }
         val result = repository.addIncome(data)
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

   private fun addExpense(data: AddExpenseDomainModel) {
      viewModelScope.launch {
         _uiState.update { it.copy(isLoading = true) }
         val result = repository.addExpense(data)
         result.fold(
            {failure->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, error = failure)
               }
            },
            {data->
               _uiState.update { currentState ->
                  currentState.copy(isLoading = false, addExpense = data)
               }
               expenditure()
            }
         )
      }
   }

   private fun onUnauthorized() {
      viewModelScope.launch {
         authUseCaseFactory.clearAccessTokenUseCase.invoke()
         userUseCaseFactory.clearUserUseCase.invoke()
         userUseCaseFactory.clearUserIdUseCase.invoke()

         _uiState.update { currentState -> currentState.copy(isAuthorized = _isAuthorized) }
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
               _uiState.update { currentState ->
                  currentState.copy(user = currentUser)
               }
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
                        _uiState.update { currentState ->
                           currentState.copy(user = user)
                        }
                     }
                  }
               }
            }
         }
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
   val addIncome: String? = null,
   val addExpense: String? = null,
   val user: User = User("John Doe","johndoe@gmail.com")
){
   fun groupExpensesByCategory(): List<Pair<String, List<ExpenseItemDomainModel>>>{
      return expenseList.groupBy {
         it.category
      }.toList()
   }
}

@Stable
sealed class UIEvents {
   class AddIncome(val data: AddIncomeDomainModel) : UIEvents()
   class AddExpense(val data: AddExpenseDomainModel) : UIEvents()
}