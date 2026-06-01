package com.financeapp.android.data.dao

import androidx.room.*
import com.financeapp.android.data.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    
    @Query("SELECT * FROM accounts ORDER BY name")
    fun getAllAccounts(): Flow<List<Account>>
    
    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccountById(id: Long): Account?
    
    @Query("SELECT * FROM accounts WHERE type = :type")
    fun getAccountsByType(type: com.financeapp.android.data.model.AccountType): Flow<List<Account>>
    
    @Query("SELECT * FROM accounts WHERE isActive = 1 ORDER BY name")
    fun getActiveAccounts(): Flow<List<Account>>
    
    @Query("SELECT SUM(balance) FROM accounts WHERE isActive = 1")
    suspend fun getTotalBalance(): Double?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: Account): Long
    
    @Update
    suspend fun updateAccount(account: Account)
    
    @Delete
    suspend fun deleteAccount(account: Account)
    
    @Query("UPDATE accounts SET balance = :balance WHERE id = :accountId")
    suspend fun updateBalance(accountId: Long, balance: Double)
}
