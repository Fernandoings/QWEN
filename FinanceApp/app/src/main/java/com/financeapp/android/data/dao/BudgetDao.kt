package com.financeapp.android.data.dao

import androidx.room.*
import com.financeapp.android.data.model.Budget
import com.financeapp.android.data.model.BudgetPeriod
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    
    @Query("SELECT * FROM budgets ORDER BY startDate DESC")
    fun getAllBudgets(): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: Long): Budget?
    
    @Query("SELECT * FROM budgets WHERE isActive = 1 AND period = :period")
    fun getActiveBudgetsByPeriod(period: BudgetPeriod): Flow<List<Budget>>
    
    @Query("SELECT * FROM budgets WHERE category = :category AND isActive = 1")
    fun getBudgetByCategory(category: String): Flow<Budget?>
    
    @Query("SELECT SUM(spentAmount) FROM budgets WHERE period = :period AND isActive = 1")
    suspend fun getTotalSpentByPeriod(period: BudgetPeriod): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: Budget): Long
    
    @Update
    suspend fun updateBudget(budget: Budget)
    
    @Delete
    suspend fun deleteBudget(budget: Budget)
    
    @Query("UPDATE budgets SET spentAmount = :spentAmount WHERE id = :budgetId")
    suspend fun updateSpentAmount(budgetId: Long, spentAmount: Double)
    
    @Query("DELETE FROM budgets WHERE isActive = 0 AND endDate < :currentTime")
    suspend fun deleteExpiredBudgets(currentTime: Long)
}
