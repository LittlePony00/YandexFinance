package com.yandex.finance.core.localdb.dao

import androidx.room.*
import com.yandex.finance.core.localdb.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM categories ORDER BY name ASC")
    suspend fun getAllCategoriesSync(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome ORDER BY name ASC")
    suspend fun getCategoriesByType(isIncome: Boolean): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE isIncome = :isIncome ORDER BY name ASC")
    suspend fun getCategoriesByTypeSync(isIncome: Boolean): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<CategoryEntity>)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("UPDATE categories SET lastSyncAt = :syncTime WHERE id = :id")
    suspend fun markAsSynced(id: Int, syncTime: Long)

    @Query("DELETE FROM categories")
    suspend fun clearAll()
} 