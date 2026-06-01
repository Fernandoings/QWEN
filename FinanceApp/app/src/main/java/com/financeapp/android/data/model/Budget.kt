package com.financeapp.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class Budget(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val amount: Double,
    val spentAmount: Double = 0.0,
    val period: BudgetPeriod, // MONTHLY, WEEKLY, YEARLY
    val startDate: Long,
    val endDate: Long?,
    val category: String? = null, // null means overall budget
    val isActive: Boolean = true
)

enum class BudgetPeriod {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    ONE_TIME
}
