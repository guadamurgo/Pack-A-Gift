package com.packagift.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Base de datos local de la app.
 *
 * Archivo: `pack_a_gift.db` (SQLite, dentro del almacenamiento privado de la app).
 * Para verla y editarla: con la app corriendo en debug, en Android Studio ir a
 * View > Tool Windows > App Inspection > Database Inspector.
 * Ahí se ven las tablas `users`, `favorites`, `cart_items`, `orders` y
 * `order_lines`, y se puede editar la columna `status` de `orders` para cambiar
 * el estado de un pedido (PLACED, PREPARING o DELIVERED).
 */
@Database(
    entities = [
        UserEntity::class,
        FavoriteEntity::class,
        CartItemEntity::class,
        OrderEntity::class,
        OrderLineEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class PackAGiftDatabase : RoomDatabase() {

    abstract fun shopDao(): ShopDao

    companion object {

        @Volatile
        private var instance: PackAGiftDatabase? = null

        fun get(context: Context): PackAGiftDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PackAGiftDatabase::class.java,
                    "pack_a_gift.db"
                ).build().also { instance = it }
            }
    }
}
