package com.packagift.app.data.local

import android.content.Context
import com.packagift.app.data.model.CartItem
import com.packagift.app.data.model.Order
import com.packagift.app.data.model.OrderLine
import com.packagift.app.data.model.OrderStatus
import org.json.JSONArray
import org.json.JSONObject

/**
 * Simple local persistence using SharedPreferences (no database).
 * Stores favourites, the cart and previous orders as JSON so data survives app restarts.
 */
class LocalStore(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // ---------------------------------------------------------------- Favourites
    fun loadFavorites(): Set<String> {
        val raw = prefs.getString(KEY_FAVORITES, null) ?: return emptySet()
        return runCatching {
            val array = JSONArray(raw)
            (0 until array.length()).map { array.getString(it) }.toSet()
        }.getOrDefault(emptySet())
    }

    fun saveFavorites(ids: Set<String>) {
        val array = JSONArray()
        ids.forEach { array.put(it) }
        prefs.edit().putString(KEY_FAVORITES, array.toString()).apply()
    }

    // ---------------------------------------------------------------- Cart
    fun loadCart(): List<CartItem> {
        val raw = prefs.getString(KEY_CART, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            (0 until array.length()).map { index ->
                val obj = array.getJSONObject(index)
                CartItem(
                    id = obj.getString("id"),
                    title = obj.getString("title"),
                    subtitle = obj.optString("subtitle"),
                    unitPrice = obj.getInt("unitPrice"),
                    quantity = obj.getInt("quantity"),
                    imageRes = obj.getInt("imageRes"),
                    isCustom = obj.optBoolean("isCustom")
                )
            }
        }.getOrDefault(emptyList())
    }

    fun saveCart(items: List<CartItem>) {
        val array = JSONArray()
        items.forEach { item ->
            array.put(
                JSONObject().apply {
                    put("id", item.id)
                    put("title", item.title)
                    put("subtitle", item.subtitle)
                    put("unitPrice", item.unitPrice)
                    put("quantity", item.quantity)
                    put("imageRes", item.imageRes)
                    put("isCustom", item.isCustom)
                }
            )
        }
        prefs.edit().putString(KEY_CART, array.toString()).apply()
    }

    // ---------------------------------------------------------------- Orders
    fun loadOrders(): List<Order> {
        val raw = prefs.getString(KEY_ORDERS, null) ?: return emptyList()
        return runCatching {
            val array = JSONArray(raw)
            (0 until array.length()).map { index ->
                val obj = array.getJSONObject(index)
                val linesArray = obj.optJSONArray("lines") ?: JSONArray()
                val lines = (0 until linesArray.length()).map { lineIndex ->
                    val lineObj = linesArray.getJSONObject(lineIndex)
                    OrderLine(
                        name = lineObj.getString("name"),
                        quantity = lineObj.getInt("quantity"),
                        price = lineObj.getInt("price")
                    )
                }
                Order(
                    id = obj.getString("id"),
                    title = obj.getString("title"),
                    subtitle = obj.optString("subtitle"),
                    imageRes = obj.getInt("imageRes"),
                    total = obj.getInt("total"),
                    dateMillis = obj.getLong("dateMillis"),
                    status = runCatching {
                        OrderStatus.valueOf(obj.getString("status"))
                    }.getOrDefault(OrderStatus.PLACED),
                    lines = lines
                )
            }
        }.getOrDefault(emptyList())
    }

    fun saveOrders(orders: List<Order>) {
        val array = JSONArray()
        orders.forEach { order ->
            val linesArray = JSONArray()
            order.lines.forEach { line ->
                linesArray.put(
                    JSONObject().apply {
                        put("name", line.name)
                        put("quantity", line.quantity)
                        put("price", line.price)
                    }
                )
            }
            array.put(
                JSONObject().apply {
                    put("id", order.id)
                    put("title", order.title)
                    put("subtitle", order.subtitle)
                    put("imageRes", order.imageRes)
                    put("total", order.total)
                    put("dateMillis", order.dateMillis)
                    put("status", order.status.name)
                    put("lines", linesArray)
                }
            )
        }
        prefs.edit().putString(KEY_ORDERS, array.toString()).apply()
    }

    private companion object {
        const val PREFS_NAME = "pack_a_gift_store"
        const val KEY_FAVORITES = "favorites"
        const val KEY_CART = "cart"
        const val KEY_ORDERS = "orders"
    }
}
