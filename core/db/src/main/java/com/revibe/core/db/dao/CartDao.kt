package com.revibe.core.db.dao

import androidx.room.*
import com.revibe.core.db.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun observeAll(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items")
    suspend fun getAll(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity)

    @Delete
    suspend fun delete(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("SELECT EXISTS(SELECT 1 FROM cart_items WHERE article = :article)")
    suspend fun isInCart(article: String): Boolean
}