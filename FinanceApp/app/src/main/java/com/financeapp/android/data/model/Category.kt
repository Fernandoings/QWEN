package com.financeapp.android.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val type: TransactionType, // INCOME or EXPENSE
    val icon: String,
    val color: String,
    val parentId: Long? = null, // For subcategories
    val isDefault: Boolean = false
)
