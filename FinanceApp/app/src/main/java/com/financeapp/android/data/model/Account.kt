package com.financeapp.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: AccountType, // CASH, BANK, CREDIT_CARD, SAVINGS, INVESTMENT
    val balance: Double,
    val currency: String = "USD",
    val icon: String? = null,
    val color: String? = null,
    val isActive: Boolean = true
)

enum class AccountType {
    CASH,
    BANK,
    CREDIT_CARD,
    SAVINGS,
    INVESTMENT,
    OTHER
}
