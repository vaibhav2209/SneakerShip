package com.example.sneakership.cart.data.local

import androidx.room.*
import com.example.sneakership.cart.domain.model.CartTotal
import com.example.sneakership.cart.domain.model.SneakerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ISneakerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItemToCart(sneaker: SneakerEntity)

    @Query("SELECT * FROM SneakerEntity")
    fun getCartItems(): Flow<List<SneakerEntity>>

    @Query("SELECT SUM(retailPrice) as total FROM SneakerEntity")
    fun getCartTotal(): Flow<CartTotal>

    @Delete
    suspend fun deleteCartItem(sneaker: SneakerEntity)

    @Query("DELETE FROM SneakerEntity")
    fun removeAllCartItems()
}