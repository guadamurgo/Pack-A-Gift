package com.packagift.app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Tablas de la base de datos local `pack_a_gift.db`.
 *
 * Se puede ver y editar con Android Studio: View > Tool Windows > App Inspection >
 * pestaña "Database Inspector", con la app corriendo en debug.
 *
 * El estado del pedido vive en [OrderEntity.status] como texto:
 * PLACED ("Pedido realizado"), PREPARING ("En preparacion"), DELIVERED ("Entregado").
 * Para cambiarlo a mano, editá esa celda y al volver a la pantalla Perfil
 * los pedidos se recargan solos.
 */

// ---------------------------------------------------------------- Usuario
@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "avatar_res") val avatarRes: Int
)

// ---------------------------------------------------------------- Favoritos
@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey @ColumnInfo(name = "pack_id") val packId: String
)

// ---------------------------------------------------------------- Carrito
@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    @ColumnInfo(name = "unit_price") val unitPrice: Int,
    val quantity: Int,
    @ColumnInfo(name = "image_res") val imageRes: Int,
    @ColumnInfo(name = "is_custom") val isCustom: Boolean
)

// ---------------------------------------------------------------- Pedidos
@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String,
    @ColumnInfo(name = "image_res") val imageRes: Int,
    val total: Int,
    @ColumnInfo(name = "date_millis") val dateMillis: Long,
    /** PLACED | PREPARING | DELIVERED (editable desde el Database Inspector). */
    val status: String
)

@Entity(
    tableName = "order_lines",
    foreignKeys = [
        ForeignKey(
            entity = OrderEntity::class,
            parentColumns = ["id"],
            childColumns = ["order_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("order_id")]
)
data class OrderLineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "order_id") val orderId: String,
    val name: String,
    val quantity: Int,
    val price: Int
)
