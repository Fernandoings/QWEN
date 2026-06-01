package com.financeapp.android.data.repository

import com.financeapp.android.data.dao.AccountDao
import com.financeapp.android.data.dao.BudgetDao
import com.financeapp.android.data.dao.CategoryDao
import com.financeapp.android.data.dao.TransactionDao
import com.financeapp.android.data.model.*
import kotlinx.coroutines.flow.Flow

class FinanceRepository(
    private val transactionDao: TransactionDao,
    private val budgetDao: BudgetDao,
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao
) {
    
    // Transaction operations
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()
    fun getTransactionsByType(type: TransactionType) = transactionDao.getTransactionsByType(type)
    fun getTransactionsByCategory(category: String) = transactionDao.getTransactionsByCategory(category)
    fun getTransactionsByDateRange(startDate: Long, endDate: Long) = 
        transactionDao.getTransactionsByDateRange(startDate, endDate)
    val recurringTransactions: Flow<List<Transaction>> = transactionDao.getRecurringTransactions()
    
    suspend fun addTransaction(transaction: Transaction) = transactionDao.insertTransaction(transaction)
    suspend fun updateTransaction(transaction: Transaction) = transactionDao.updateTransaction(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.deleteTransaction(transaction)
    suspend fun deleteTransactionById(id: Long) = transactionDao.deleteTransactionById(id)
    suspend fun getTotalByTypeAndDateRange(type: TransactionType, startDate: Long, endDate: Long) =
        transactionDao.getTotalByTypeAndDateRange(type, startDate, endDate)
    suspend fun getTotalByCategoryAndDateRange(category: String, startDate: Long, endDate: Long) =
        transactionDao.getTotalByCategoryAndDateRange(category, startDate, endDate)
    
    // Budget operations
    val allBudgets: Flow<List<Budget>> = budgetDao.getAllBudgets()
    fun getActiveBudgetsByPeriod(period: BudgetPeriod) = budgetDao.getActiveBudgetsByPeriod(period)
    fun getBudgetByCategory(category: String) = budgetDao.getBudgetByCategory(category)
    
    suspend fun addBudget(budget: Budget) = budgetDao.insertBudget(budget)
    suspend fun updateBudget(budget: Budget) = budgetDao.updateBudget(budget)
    suspend fun deleteBudget(budget: Budget) = budgetDao.deleteBudget(budget)
    suspend fun updateSpentAmount(budgetId: Long, spentAmount: Double) = 
        budgetDao.updateSpentAmount(budgetId, spentAmount)
    
    // Account operations
    val allAccounts: Flow<List<Account>> = accountDao.getAllAccounts()
    val activeAccounts: Flow<List<Account>> = accountDao.getActiveAccounts()
    fun getAccountsByType(type: AccountType) = accountDao.getAccountsByType(type)
    
    suspend fun addAccount(account: Account) = accountDao.insertAccount(account)
    suspend fun updateAccount(account: Account) = accountDao.updateAccount(account)
    suspend fun deleteAccount(account: Account) = accountDao.deleteAccount(account)
    suspend fun updateBalance(accountId: Long, balance: Double) = 
        accountDao.updateBalance(accountId, balance)
    suspend fun getTotalBalance() = accountDao.getTotalBalance()
    
    // Category operations
    val allCategories: Flow<List<Category>> = categoryDao.getAllCategories()
    fun getCategoriesByType(type: TransactionType) = categoryDao.getCategoriesByType(type)
    val defaultCategories: Flow<List<Category>> = categoryDao.getDefaultCategories()
    val parentCategories: Flow<List<Category>> = categoryDao.getParentCategories()
    fun getSubcategories(parentId: Long) = categoryDao.getSubcategories(parentId)
    
    suspend fun addCategory(category: Category) = categoryDao.insertCategory(category)
    suspend fun updateCategory(category: Category) = categoryDao.updateCategory(category)
    suspend fun deleteCategory(category: Category) = categoryDao.deleteCategory(category)
}
