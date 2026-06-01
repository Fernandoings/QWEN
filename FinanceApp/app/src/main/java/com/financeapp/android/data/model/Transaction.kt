package com.financeapp.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val type: TransactionType, // INCOME or EXPENSE
    val category: String,
    val description: String,
    val date: Long, // Timestamp
    val isRecurring: Boolean = false,
    val recurringFrequency: RecurringFrequency? = null, // DAILY, WEEKLY, MONTHLY, YEARLY
    val budgetId: Long? = null,
    val accountId: Long? = null
)

enum class TransactionType {
    INCOME,
    EXPENSE
}

enum class RecurringFrequency {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}
