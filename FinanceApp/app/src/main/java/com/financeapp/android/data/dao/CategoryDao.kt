package com.financeapp.android.data.dao

import androidx.room.*
import com.financeapp.android.data.model.Category
import com.financeapp.android.data.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    
    @Query("SELECT * FROM categories ORDER BY name")
    fun getAllCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Long): Category?
    
    @Query("SELECT * FROM categories WHERE type = :type ORDER BY name")
    fun getCategoriesByType(type: TransactionType): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE isDefault = 1 ORDER BY name")
    fun getDefaultCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE parentId IS NULL ORDER BY name")
    fun getParentCategories(): Flow<List<Category>>
    
    @Query("SELECT * FROM categories WHERE parentId = :parentId ORDER BY name")
    fun getSubcategories(parentId: Long): Flow<List<Category>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: Category): Long
    
    @Update
    suspend fun updateCategory(category: Category)
    
    @Delete
    suspend fun deleteCategory(category: Category)
}
