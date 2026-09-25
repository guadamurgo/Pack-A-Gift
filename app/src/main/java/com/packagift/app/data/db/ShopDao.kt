package com.packagift.app.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShopDao {

    // ------------------------------------------------------------ Usuario
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getUser(id: String): UserEntity?

    // ------------------------------------------------------------ Favoritos
    @Query("SELECT pack_id FROM favorites")
    suspend fun getFavoriteIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE pack_id = :packId")
    suspend fun removeFavorite(packId: String)

    @Query("DELETE FROM favorites")
    suspend fun clearFavorites()

    // ------------------------------------------------------------ Carrito
    @Query("SELECT * FROM cart_items")
    suspend fun getCartItems(): List<CartItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCartItem(item: CartItemEntity)

    @Query("DELETE FROM cart_items WHERE id = :id")
    suspend fun deleteCartItem(id: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // ------------------------------------------------------------ Pedidos
    @Query("SELECT * FROM orders ORDER BY date_millis DESC")
    suspend fun getOrders(): List<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM order_lines WHERE order_id = :orderId")
    suspend fun getOrderLines(orderId: String): List<OrderLineEntity>

    @Insert
    suspend fun insertOrderLines(lines: List<OrderLineEntity>)

    /** Cambia el estado de un pedido (PLACED / PREPARING / DELIVERED). */
    @Query("UPDATE orders SET status = :status WHERE id = :orderId")
    suspend fun updateOrderStatus(orderId: String, status: String)
}
