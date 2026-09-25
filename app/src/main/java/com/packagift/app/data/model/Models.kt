package com.packagift.app.data.model

import androidx.annotation.DrawableRes

/** A date, celebration or specific occasion (used both for pre-made packs and the Create flow). */
data class Occasion(
    val id: String,
    val name: String,
    @DrawableRes val imageRes: Int
)

/** A generic product that can be part of a pack (mini cake, additional, etc.). */
data class Product(
    val id: String,
    val name: String,
    val price: Int,
    @DrawableRes val imageRes: Int
)

/** A pre-made pack, always tied to a concrete occasion. */
data class Pack(
    val id: String,
    val name: String,
    val occasionId: String,
    val occasionName: String,
    val description: String,
    val price: Int,
    @DrawableRes val imageRes: Int,
    val items: List<String>,
    val isTrending: Boolean = false
)

/** The physical shape of the empty box/container chosen in the Create flow. */
data class BoxShape(
    val id: String,
    val name: String,
    @DrawableRes val imageRes: Int
)

/** A mini cake option for the Create flow. */
data class MiniCake(
    val id: String,
    val name: String,
    @DrawableRes val imageRes: Int
)

data class User(
    val id: String,
    val name: String,
    @DrawableRes val avatarRes: Int
)

enum class OrderStatus(val label: String) {
    PLACED("Pedido realizado"),
    PREPARING("En preparacion"),
    DELIVERED("Entregado")
}

/** Size of a custom pack: always 1 mini cake + N additionals.
 * Base prices are the final Excel sale prices (Personalizado simple/intermedio/grande). */
enum class PackageSize(val id: String, val label: String, val additionalCount: Int, val basePrice: Int) {
    SIMPLE("simple", "Simple", 3, 27500),
    INTERMEDIA("intermedia", "Intermedia", 5, 34000),
    GRANDE("grande", "Grande", 7, 37500)
}

data class CartItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val unitPrice: Int,
    val quantity: Int,
    @DrawableRes val imageRes: Int,
    val isCustom: Boolean = false
) {
    val lineTotal: Int get() = unitPrice * quantity
}

data class OrderLine(
    val name: String,
    val quantity: Int,
    val price: Int
)

data class Order(
    val id: String,
    val title: String,
    val subtitle: String,
    @DrawableRes val imageRes: Int,
    val total: Int,
    val dateMillis: Long,
    val status: OrderStatus,
    val lines: List<OrderLine>
)
