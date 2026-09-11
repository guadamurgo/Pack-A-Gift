package com.packagift.app.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.packagift.app.data.local.LocalStore
import com.packagift.app.data.mock.MockData
import com.packagift.app.data.model.BoxShape
import com.packagift.app.data.model.CartItem
import com.packagift.app.data.model.MiniCake
import com.packagift.app.data.model.Occasion
import com.packagift.app.data.model.Order
import com.packagift.app.data.model.OrderLine
import com.packagift.app.data.model.OrderStatus
import com.packagift.app.data.model.Pack
import com.packagift.app.data.model.PackageSize
import java.util.UUID

class PackAGiftViewModel(application: Application) : AndroidViewModel(application) {

    private val store = LocalStore(application)

    // --------------------------------------------------------------- Catalog (mock data)
    val user get() = MockData.user
    val occasions get() = MockData.occasions
    val createOccasions get() = MockData.createOccasions
    val trendingPacks get() = MockData.trendingPacks
    val boxShapes get() = MockData.boxShapes
    val miniCakes get() = MockData.miniCakes
    val additionalProducts get() = MockData.additionalProducts

    fun packsForOccasion(occasionId: String): List<Pack> = MockData.packsForOccasion(occasionId)
    fun packById(id: String): Pack? = MockData.packById(id)
    fun occasionById(id: String?): Occasion? = MockData.occasionById(id)
    fun boxById(id: String?): BoxShape? = MockData.boxById(id)
    fun miniCakeById(id: String?): MiniCake? = MockData.miniCakeById(id)

    // --------------------------------------------------------------- User data (persisted)
    var favoriteIds: Set<String> by mutableStateOf(emptySet())
        private set

    var cartItems: List<CartItem> by mutableStateOf(emptyList())
        private set

    var orders: List<Order> by mutableStateOf(emptyList())
        private set

    init {
        favoriteIds = store.loadFavorites()
        cartItems = store.loadCart()
        orders = store.loadOrders()
    }

    // --------------------------------------------------------------- Favourites
    fun isFavorite(packId: String): Boolean = packId in favoriteIds

    fun toggleFavorite(packId: String) {
        favoriteIds = if (packId in favoriteIds) favoriteIds - packId else favoriteIds + packId
        store.saveFavorites(favoriteIds)
    }

    val favoritePacks: List<Pack>
        get() = MockData.packs.filter { it.id in favoriteIds }

    // --------------------------------------------------------------- Cart
    val cartCount: Int get() = cartItems.sumOf { it.quantity }
    val cartSubtotal: Int get() = cartItems.sumOf { it.lineTotal }

    fun addPackToCart(pack: Pack) {
        val lineId = "pack:${pack.id}"
        val existing = cartItems.firstOrNull { it.id == lineId }
        cartItems = if (existing != null) {
            cartItems.map { if (it.id == lineId) it.copy(quantity = it.quantity + 1) else it }
        } else {
            cartItems + CartItem(
                id = lineId,
                title = pack.name,
                subtitle = pack.occasionName,
                unitPrice = pack.price,
                quantity = 1,
                imageRes = pack.imageRes
            )
        }
        store.saveCart(cartItems)
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        cartItems = if (quantity <= 0) {
            cartItems.filterNot { it.id == itemId }
        } else {
            cartItems.map { if (it.id == itemId) it.copy(quantity = quantity) else it }
        }
        store.saveCart(cartItems)
    }

    fun removeFromCart(itemId: String) {
        cartItems = cartItems.filterNot { it.id == itemId }
        store.saveCart(cartItems)
    }

    /** Re-adds every line of a previous order back into the cart. */
    fun addOrderToCart(order: Order) {
        var updated = cartItems
        order.lines.forEach { line ->
            val pack = MockData.packs.firstOrNull { it.name == line.name }
            val lineId = pack?.let { "pack:${it.id}" } ?: "order:${UUID.randomUUID()}"
            val existing = updated.firstOrNull { it.id == lineId }
            updated = if (existing != null) {
                updated.map {
                    if (it.id == lineId) it.copy(quantity = it.quantity + line.quantity) else it
                }
            } else {
                updated + CartItem(
                    id = lineId,
                    title = line.name,
                    subtitle = pack?.occasionName ?: order.subtitle,
                    unitPrice = line.price,
                    quantity = line.quantity,
                    imageRes = pack?.imageRes ?: order.imageRes
                )
            }
        }
        cartItems = updated
        store.saveCart(cartItems)
    }

    fun confirmOrder() {
        if (cartItems.isEmpty()) return
        val order = Order(
            id = UUID.randomUUID().toString(),
            title = if (cartItems.size == 1) cartItems.first().title
            else "Pedido con ${cartItems.size} packs",
            subtitle = cartItems.joinToString(" · ") { it.title },
            imageRes = cartItems.first().imageRes,
            total = cartSubtotal,
            dateMillis = System.currentTimeMillis(),
            status = OrderStatus.PLACED,
            lines = cartItems.map { OrderLine(it.title, it.quantity, it.unitPrice) }
        )
        orders = listOf(order) + orders
        store.saveOrders(orders)
        cartItems = emptyList()
        store.saveCart(cartItems)
    }

    // --------------------------------------------------------------- Create flow
    var step: Int by mutableIntStateOf(0)
        private set

    var selectedOccasion: Occasion? by mutableStateOf(null)
        private set

    var selectedBox: BoxShape? by mutableStateOf(null)
        private set

    var selectedSize: PackageSize? by mutableStateOf(null)
        private set

    var selectedCake: MiniCake? by mutableStateOf(null)
        private set

    var selectedAdditionals: Set<String> by mutableStateOf(emptySet())
        private set

    var customComment: String by mutableStateOf("")
        private set

    val additionalLimit: Int get() = selectedSize?.additionalCount ?: 0

    val customTotal: Int?
        get() {
            val size = selectedSize ?: return null
            val extras = selectedAdditionals
                .mapNotNull { MockData.productById(it) }
                .sumOf { it.price }
            return size.basePrice + extras
        }

    fun selectOccasion(occasion: Occasion) {
        selectedOccasion = occasion
    }

    fun selectBox(box: BoxShape) {
        selectedBox = box
    }

    fun selectSize(size: PackageSize) {
        selectedSize = size
        if (selectedAdditionals.size > size.additionalCount) {
            selectedAdditionals = selectedAdditionals.take(size.additionalCount).toSet()
        }
    }

    fun selectCake(cake: MiniCake) {
        selectedCake = cake
    }

    fun updateComment(value: String) {
        customComment = value
    }

    /** @return true when the product could be toggled, false when the limit was reached. */
    fun toggleAdditional(productId: String): Boolean {
        return if (productId in selectedAdditionals) {
            selectedAdditionals = selectedAdditionals - productId
            true
        } else {
            if (selectedAdditionals.size >= additionalLimit) {
                false
            } else {
                selectedAdditionals = selectedAdditionals + productId
                true
            }
        }
    }

    fun canGoNext(): Boolean = when (step) {
        0 -> selectedOccasion != null
        1 -> selectedBox != null
        2 -> selectedSize != null
        3 -> selectedCake != null
        4 -> selectedAdditionals.isNotEmpty()
        else -> false
    }

    fun nextStep() {
        if (step < 5) step++
    }

    fun previousStep() {
        if (step > 0) step--
    }

    fun goToStep(index: Int) {
        step = index.coerceIn(0, 5)
    }

    fun addCustomToCart(): Boolean {
        val total = customTotal ?: return false
        val occasionName = selectedOccasion?.name ?: "Sin ocasión"
        val boxName = selectedBox?.name ?: "Caja"
        val cakeName = selectedCake?.name ?: "Minitorta"
        val baseSubtitle = "$boxName · Minitorta de $cakeName + ${selectedAdditionals.size} adicionales"
        val subtitle = if (customComment.isNotBlank()) "$baseSubtitle · \"${customComment.trim()}\"" else baseSubtitle

        cartItems = cartItems + CartItem(
            id = "custom:${UUID.randomUUID()}",
            title = "Pack personalizado — $occasionName",
            subtitle = subtitle,
            unitPrice = total,
            quantity = 1,
            imageRes = selectedBox?.imageRes ?: MockData.logoRes,
            isCustom = true
        )
        store.saveCart(cartItems)
        resetCreate()
        return true
    }

    fun resetCreate() {
        step = 0
        selectedOccasion = null
        selectedBox = null
        selectedSize = null
        selectedCake = null
        selectedAdditionals = emptySet()
        customComment = ""
    }
}
