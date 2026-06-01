package com.financeapp.android.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.financeapp.android.data.dao.AccountDao
import com.financeapp.android.data.dao.BudgetDao
import com.financeapp.android.data.dao.CategoryDao
import com.financeapp.android.data.dao.TransactionDao
import com.financeapp.android.data.model.Account
import com.financeapp.android.data.model.Budget
import com.financeapp.android.data.model.Category
import com.financeapp.android.data.model.Transaction

@Database(
    entities = [
        Transaction::class,
        Budget::class,
        Account::class,
        Category::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {
    
    abstract fun transactionDao(): TransactionDao
    abstract fun budgetDao(): BudgetDao
    abstract fun accountDao(): AccountDao
    abstract fun categoryDao(): CategoryDao
    
    companion object {
        @Volatile
        private var INSTANCE: FinanceDatabase? = null
        
        fun getDatabase(context: Context): FinanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
