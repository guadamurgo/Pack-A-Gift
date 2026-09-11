package com.packagift.app.ui.navigation

object Routes {
    const val HIGHLIGHTS = "highlights"
    const val CREATE = "create"
    const val PROFILE = "profile"
    const val CART = "cart"
    const val PACK_DETAIL = "pack/{packId}"

    fun packDetail(packId: String): String = "pack/$packId"
}
