package com.packagift.app.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.packagift.app.data.db.ShopRepository
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
import com.packagift.app.data.model.User
import java.util.UUID
import kotlinx.coroutines.launch

class PackAGiftViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopRepository(application)

    // --------------------------------------------------------------- Catalog (mock data)
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

    // --------------------------------------------------------------- User data (Room database)
    var user: User by mutableStateOf(MockData.user)
        private set

    var favoriteIds: Set<String> by mutableStateOf(emptySet())
        private set

    var cartItems: List<CartItem> by mutableStateOf(emptyList())
        private set

    var orders: List<Order> by mutableStateOf(emptyList())
        private set

    init {
        viewModelScope.launch {
            repository.migrateIfNeeded()
            refreshAll()
        }
    }

    /** Recarga todo desde la base (usuario, favoritos, carrito y pedidos). */
    fun refreshAll() {
        viewModelScope.launch {
            user = repository.getUser()
            favoriteIds = repository.getFavoriteIds()
            cartItems = repository.getCartItems()
            refreshOrders()
        }
    }

    /**
     * Recarga solo los pedidos. Llamalo al entrar a Perfil para ver cambios
     * hechos a mano en la base (columna `status` de la tabla `orders`).
     */
    fun refreshOrders() {
        viewModelScope.launch {
            orders = repository.getOrders().map { order ->
                if (order.status == OrderStatus.PLACED) order.copy(status = OrderStatus.PREPARING) else order
            }
        }
    }

    // --------------------------------------------------------------- Favourites
    fun isFavorite(packId: String): Boolean = packId in favoriteIds

    fun toggleFavorite(packId: String) {
        favoriteIds = if (packId in favoriteIds) favoriteIds - packId else favoriteIds + packId
        viewModelScope.launch {
            if (packId in favoriteIds) repository.addFavorite(packId)
            else repository.removeFavorite(packId)
        }
    }

    val favoritePacks: List<Pack>
        get() = MockData.packs.filter { it.id in favoriteIds }

    // --------------------------------------------------------------- Cart
    val cartCount: Int get() = cartItems.sumOf { it.quantity }
    val cartSubtotal: Int get() = cartItems.sumOf { it.lineTotal }

    fun addPackToCart(pack: Pack) {
        val lineId = "pack:${pack.id}"
        val existing = cartItems.firstOrNull { it.id == lineId }
        val updated = if (existing != null) {
            existing.copy(quantity = existing.quantity + 1)
        } else {
            CartItem(
                id = lineId,
                title = pack.name,
                subtitle = pack.occasionName,
                unitPrice = pack.price,
                quantity = 1,
                imageRes = pack.imageRes
            )
        }
        cartItems = if (existing != null) {
            cartItems.map { if (it.id == lineId) updated else it }
        } else {
            cartItems + updated
        }
        viewModelScope.launch { repository.upsertCartItem(updated) }
    }

    fun updateQuantity(itemId: String, quantity: Int) {
        if (quantity <= 0) {
            removeFromCart(itemId)
            return
        }
        cartItems = cartItems.map { if (it.id == itemId) it.copy(quantity = quantity) else it }
        val updated = cartItems.firstOrNull { it.id == itemId } ?: return
        viewModelScope.launch { repository.upsertCartItem(updated) }
    }

    fun removeFromCart(itemId: String) {
        cartItems = cartItems.filterNot { it.id == itemId }
        viewModelScope.launch { repository.deleteCartItem(itemId) }
    }

    /**
     * "Confirmar pedido" NO cobra nada real: solo guarda el pedido en la tabla
     * `orders` (con sus líneas en `order_lines`), vacía el carrito y lo muestra
     * en Perfil > Compras anteriores.
     */
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
            status = OrderStatus.PREPARING,
            lines = cartItems.map { OrderLine(it.title, it.quantity, it.unitPrice) }
        )
        orders = listOf(order) + orders
        cartItems = emptyList()
        viewModelScope.launch {
            repository.insertOrder(order)
            repository.clearCart()
        }
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

        val item = CartItem(
            id = "custom:${UUID.randomUUID()}",
            title = "Pack personalizado — $occasionName",
            subtitle = subtitle,
            unitPrice = total,
            quantity = 1,
            imageRes = selectedBox?.imageRes ?: MockData.logoRes,
            isCustom = true
        )
        cartItems = cartItems + item
        viewModelScope.launch { repository.upsertCartItem(item) }
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
