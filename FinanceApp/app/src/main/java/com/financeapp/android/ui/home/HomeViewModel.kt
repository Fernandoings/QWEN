package com.financeapp.android.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.financeapp.android.data.model.BudgetPeriod
import com.financeapp.android.data.model.TransactionType
import com.financeapp.android.data.repository.FinanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: FinanceRepository
    
    // State flows for UI
    private val _totalBalance = MutableStateFlow<Double?>(null)
    val totalBalance: StateFlow<Double?> = _totalBalance.asStateFlow()
    
    private val _monthlyIncome = MutableStateFlow<Double?>(null)
    val monthlyIncome: StateFlow<Double?> = _monthlyIncome.asStateFlow()
    
    private val _monthlyExpenses = MutableStateFlow<Double?>(null)
    val monthlyExpenses: StateFlow<Double?> = _monthlyExpenses.asStateFlow()
    
    private val _recentTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val recentTransactions: StateFlow<List<Transaction>> = _recentTransactions.asStateFlow()
    
    private val _activeBudgets = MutableStateFlow<List<Budget>>(emptyList())
    val activeBudgets: StateFlow<List<Budget>> = _activeBudgets.asStateFlow()

    init {
        val database = FinanceDatabase.getDatabase(application)
        repository = FinanceRepository(
            database.transactionDao(),
            database.budgetDao(),
            database.accountDao(),
            database.categoryDao()
        )
        
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            repository.activeAccounts.collect { accounts ->
                // Calculate total balance from all accounts
                _totalBalance.value = accounts.sumOf { it.balance }
            }
        }
        
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val startOfMonth = getStartOfMonth(calendar)
            val endOfMonth = getEndOfMonth(calendar)
            
            repository.getTotalByTypeAndDateRange(
                TransactionType.INCOME,
                startOfMonth,
                endOfMonth
            )?.let { _monthlyIncome.value = it }
        }
        
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val startOfMonth = getStartOfMonth(calendar)
            val endOfMonth = getEndOfMonth(calendar)
            
            repository.getTotalByTypeAndDateRange(
                TransactionType.EXPENSE,
                startOfMonth,
                endOfMonth
            )?.let { _monthlyExpenses.value = it }
        }
        
        viewModelScope.launch {
            repository.allTransactions.collect { transactions ->
                _recentTransactions.value = transactions.take(10) // Last 10 transactions
            }
        }
        
        viewModelScope.launch {
            repository.getActiveBudgetsByPeriod(BudgetPeriod.MONTHLY).collect { budgets ->
                _activeBudgets.value = budgets
            }
        }
    }

    fun refreshData() {
        loadData()
    }

    private fun getStartOfMonth(calendar: Calendar): Long {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    private fun getEndOfMonth(calendar: Calendar): Long {
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return calendar.timeInMillis
    }
}
