package com.packagift.app.data.db

import android.content.Context
import com.packagift.app.data.local.LocalStore
import com.packagift.app.data.mock.MockData
import com.packagift.app.data.model.CartItem
import com.packagift.app.data.model.Order
import com.packagift.app.data.model.OrderLine
import com.packagift.app.data.model.OrderStatus
import com.packagift.app.data.model.User

/**
 * Única puerta de entrada a la base de datos.
 *
 * En el primer arranque copia a Room lo que hubiera guardado en el formato
 * anterior (SharedPreferences de [LocalStore]: favoritos, carrito y pedidos),
 * así no se pierde nada. No hay pagos reales: "Confirmar pedido" solo guarda
 * el pedido en la tabla `orders` con estado PREPARING.
 */
class ShopRepository(context: Context) {

    private val appContext = context.applicationContext
    private val dao = PackAGiftDatabase.get(appContext).shopDao()
    private val prefs = appContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    /** Copia por única vez los datos viejos (si existen) a Room. */
    suspend fun migrateIfNeeded() {
        if (prefs.getBoolean(KEY_MIGRATED, false)) return
        val store = LocalStore(appContext)

        val favorites = store.loadFavorites()
        favorites.forEach { dao.addFavorite(FavoriteEntity(it)) }

        store.loadCart().forEach { dao.upsertCartItem(it.toEntity()) }

        store.loadOrders().forEach { order ->
            dao.insertOrder(order.toEntity())
            dao.insertOrderLines(order.lines.map { it.toEntity(order.id) })
        }

        // Usuario por defecto si la tabla está vacía.
        if (dao.getUser(DEFAULT_USER_ID) == null) {
            dao.upsertUser(UserEntity(DEFAULT_USER_ID, MockData.user.name, MockData.user.avatarRes))
        }

        prefs.edit().putBoolean(KEY_MIGRATED, true).apply()
    }

    // ------------------------------------------------------------ Usuario
    suspend fun getUser(): User {
        val entity = dao.getUser(DEFAULT_USER_ID)
            ?: UserEntity(DEFAULT_USER_ID, MockData.user.name, MockData.user.avatarRes)
                .also { dao.upsertUser(it) }
        return User(entity.id, entity.name, entity.avatarRes)
    }

    // ------------------------------------------------------------ Favoritos
    suspend fun getFavoriteIds(): Set<String> = dao.getFavoriteIds().toSet()

    suspend fun addFavorite(packId: String) {
        dao.addFavorite(FavoriteEntity(packId))
    }

    suspend fun removeFavorite(packId: String) {
        dao.removeFavorite(packId)
    }

    // ------------------------------------------------------------ Carrito
    suspend fun getCartItems(): List<CartItem> = dao.getCartItems().map { it.toModel() }

    suspend fun upsertCartItem(item: CartItem) {
        dao.upsertCartItem(item.toEntity())
    }

    suspend fun deleteCartItem(id: String) {
        dao.deleteCartItem(id)
    }

    suspend fun clearCart() {
        dao.clearCart()
    }

    // ------------------------------------------------------------ Pedidos
    suspend fun getOrders(): List<Order> =
        dao.getOrders().map { entity ->
            val lines = dao.getOrderLines(entity.id).map { it.toModel() }
            entity.toModel(lines)
        }

    suspend fun insertOrder(order: Order) {
        dao.insertOrder(order.toEntity())
        dao.insertOrderLines(order.lines.map { it.toEntity(order.id) })
    }

    /** Cambia el estado de un pedido (PLACED / PREPARING / DELIVERED). */
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus) {
        dao.updateOrderStatus(orderId, status.name)
    }

    private companion object {
        const val PREFS = "pack_a_gift_db"
        const val KEY_MIGRATED = "room_migrated"
        const val DEFAULT_USER_ID = "u1"
    }
}

// ------------------------------------------------------------------ Mappers
private fun CartItem.toEntity() = CartItemEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    unitPrice = unitPrice,
    quantity = quantity,
    imageRes = imageRes,
    isCustom = isCustom
)

private fun CartItemEntity.toModel() = CartItem(
    id = id,
    title = title,
    subtitle = subtitle,
    unitPrice = unitPrice,
    quantity = quantity,
    imageRes = imageRes,
    isCustom = isCustom
)

private fun Order.toEntity() = OrderEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    imageRes = imageRes,
    total = total,
    dateMillis = dateMillis,
    status = status.name
)

private fun OrderEntity.toModel(lines: List<OrderLine>) = Order(
    id = id,
    title = title,
    subtitle = subtitle,
    imageRes = imageRes,
    total = total,
    dateMillis = dateMillis,
    status = runCatching { OrderStatus.valueOf(status) }
        .getOrDefault(OrderStatus.PLACED),
    lines = lines
)

private fun OrderLine.toEntity(orderId: String) = OrderLineEntity(
    orderId = orderId,
    name = name,
    quantity = quantity,
    price = price
)

private fun OrderLineEntity.toModel() = OrderLine(
    name = name,
    quantity = quantity,
    price = price
)
