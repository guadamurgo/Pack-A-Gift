package com.packagift.app.data.mock

import com.packagift.app.R
import com.packagift.app.data.model.BoxShape
import com.packagift.app.data.model.MiniCake
import com.packagift.app.data.model.Occasion
import com.packagift.app.data.model.Pack
import com.packagift.app.data.model.Product
import com.packagift.app.data.model.User

/**
 * All local/mock data for Pack-A-Gift.
 *
 * Everything the UI consumes lives here so it can later be replaced by a database or API
 * without touching the composables.
 */
object MockData {

    /** Uniform price for every pre-made pack. */
    const val PACK_PRICE = 5000

    /** Only these additional products add an extra cost. */
    const val EXTRA_PRODUCT_SURCHARGE = 1000

    val user = User(
        id = "u1",
        name = "Usuario 1",
        avatarRes = R.drawable.ic_avatar
    )

    val logoRes: Int = R.drawable.ic_gift

    // ---------------------------------------------------------------------------------------
    // Occasions
    // ---------------------------------------------------------------------------------------
    val occasions: List<Occasion> = listOf(
        Occasion("madre", "Día de la Madre", R.drawable.ic_card),
        Occasion("padre", "Día del Padre", R.drawable.ic_cup),
        Occasion("amigo", "Día del Amigo", R.drawable.ic_bracelet),
        Occasion("sanvalentin", "San Valentín", R.drawable.ic_card),
        Occasion("aniversario", "Aniversario", R.drawable.ic_letter),
        Occasion("cumpleanos", "Cumpleaños", R.drawable.ic_balloon),
        Occasion("graduacion", "Graduación", R.drawable.ic_pen),
        Occasion("navidad", "Navidad", R.drawable.ic_ornament),
        Occasion("pascuas", "Pascuas", R.drawable.ic_candy),
        Occasion("dianino", "Día del Niño", R.drawable.ic_balloon)
    )

    /** Extra option offered in the Create flow. */
    val otherOccasion = Occasion("otra", "Otra ocasión", R.drawable.ic_gift)

    val createOccasions: List<Occasion> = occasions + otherOccasion

    // ---------------------------------------------------------------------------------------
    // Packs (pre-made, always tied to an occasion)
    // ---------------------------------------------------------------------------------------
    private fun pack(
        id: String,
        name: String,
        occasionId: String,
        occasionName: String,
        imageRes: Int,
        description: String,
        items: List<String>,
        trending: Boolean = false
    ) = Pack(
        id = id,
        name = name,
        occasionId = occasionId,
        occasionName = occasionName,
        description = description,
        price = PACK_PRICE,
        imageRes = imageRes,
        items = items,
        isTrending = trending
    )

    val packs: List<Pack> = listOf(
        // Día de la Madre
        pack(
            "m1", "Pack Abrazo de Mamá", "madre", "Día de la Madre", R.drawable.ic_cake,
            "Un regalo dulce pensado para agradecer a mamá en su día.",
            listOf("Minitorta", "Taza", "Chocolate", "Vela", "Tarjeta"), trending = true
        ),
        pack(
            "m2", "Pack Flores y Dulzura", "madre", "Día de la Madre", R.drawable.ic_card,
            "Detalles delicados para sorprender a mamá.",
            listOf("Minitorta", "Chocolate", "Vela aromática", "Tarjeta", "Taza")
        ),
        pack(
            "m3", "Pack Mañana de Mamá", "madre", "Día de la Madre", R.drawable.ic_cup,
            "Un desayuno dulce para empezar su día especial.",
            listOf("Minitorta", "Taza", "Snacks", "Chocolate", "Tarjeta")
        ),

        // Día del Padre
        pack(
            "p1", "Pack Para Papá", "padre", "Día del Padre", R.drawable.ic_cup,
            "El combo clásico para festejar a papá.",
            listOf("Minitorta", "Taza", "Chocolate", "Llavero", "Tarjeta"), trending = true
        ),
        pack(
            "p2", "Pack Cracks", "padre", "Día del Padre", R.drawable.ic_snacks,
            "Snacks y dulces para compartir con papá.",
            listOf("Minitorta", "Snacks", "Chocolate", "Llavero", "Tarjeta")
        ),
        pack(
            "p3", "Pack Festejo de Papá", "padre", "Día del Padre", R.drawable.ic_cake,
            "Un detalle completo para su día.",
            listOf("Minitorta", "Chocolate", "Snacks", "Llavero", "Tarjeta")
        ),

        // Día del Amigo
        pack(
            "a1", "Pack Bestie", "amigo", "Día del Amigo", R.drawable.ic_bracelet,
            "Para esa amistad que lo es todo.",
            listOf("Minitorta", "Snacks", "Chocolates", "Pulsera", "Tarjeta"), trending = true
        ),
        pack(
            "a2", "Pack Noche de Amigos", "amigo", "Día del Amigo", R.drawable.ic_snacks,
            "Todo listo para una juntada inolvidable.",
            listOf("Minitorta", "Snacks", "Chocolates", "Stickers", "Tarjeta")
        ),
        pack(
            "a3", "Pack Recuerdo", "amigo", "Día del Amigo", R.drawable.ic_card,
            "Un recuerdo dulce para una amistad especial.",
            listOf("Minitorta", "Chocolate", "Pulsera", "Carta", "Tarjeta")
        ),

        // San Valentín
        pack(
            "v1", "Pack Amor Eterno", "sanvalentin", "San Valentín", R.drawable.ic_card,
            "Una declaración dulce para tu persona especial.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Peluche"), trending = true
        ),
        pack(
            "v2", "Pack Corazón", "sanvalentin", "San Valentín", R.drawable.ic_candy,
            "El clásico romántico que nunca falla.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Pulsera")
        ),
        pack(
            "v3", "Pack Cita Perfecta", "sanvalentin", "San Valentín", R.drawable.ic_cake,
            "Para una noche especial en pareja.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Detalle")
        ),

        // Aniversario
        pack(
            "n1", "Pack Nuestro Aniversario", "aniversario", "Aniversario", R.drawable.ic_letter,
            "Celebrá el tiempo compartido con un detalle único.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Detalle"), trending = true
        ),
        pack(
            "n2", "Pack Para Recordar", "aniversario", "Aniversario", R.drawable.ic_cake,
            "Un recuerdo dulce de un momento especial.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Pulsera")
        ),
        pack(
            "n3", "Pack Años Juntos", "aniversario", "Aniversario", R.drawable.ic_cup,
            "Para festejar los años compartidos.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Taza")
        ),

        // Cumpleaños
        pack(
            "c1", "Pack Sorpresa", "cumpleanos", "Cumpleaños", R.drawable.ic_balloon,
            "La sorpresa ideal para un cumpleaños.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Tarjeta"), trending = true
        ),
        pack(
            "c2", "Pack Fiesta", "cumpleanos", "Cumpleaños", R.drawable.ic_candy,
            "Todo para armar la fiesta perfecta.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Stickers")
        ),
        pack(
            "c3", "Pack Cumple Feliz", "cumpleanos", "Cumpleaños", R.drawable.ic_cake,
            "Un detalle alegre para decir feliz cumpleaños.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Tarjeta")
        ),

        // Graduación
        pack(
            "g1", "Pack Egresado", "graduacion", "Graduación", R.drawable.ic_pen,
            "Para celebrar un gran logro.",
            listOf("Minitorta", "Chocolates", "Taza", "Lapicera", "Tarjeta"), trending = true
        ),
        pack(
            "g2", "Pack Futuro Brillante", "graduacion", "Graduación", R.drawable.ic_sticker,
            "Un empujón dulce hacia lo que viene.",
            listOf("Minitorta", "Chocolates", "Lapicera", "Stickers", "Tarjeta")
        ),
        pack(
            "g3", "Pack Logro", "graduacion", "Graduación", R.drawable.ic_cup,
            "Para festejar el esfuerzo y el logro.",
            listOf("Minitorta", "Chocolates", "Taza", "Lapicera", "Tarjeta")
        ),

        // Navidad
        pack(
            "x1", "Pack Navidad Mágica", "navidad", "Navidad", R.drawable.ic_ornament,
            "El espíritu navideño en un pack.",
            listOf("Minitorta", "Dulces navideños", "Chocolate", "Adorno", "Tarjeta"), trending = true
        ),
        pack(
            "x2", "Pack Nochebuena", "navidad", "Navidad", R.drawable.ic_candle,
            "Para compartir en la mesa de Nochebuena.",
            listOf("Minitorta", "Dulces navideños", "Chocolate", "Adorno", "Vela")
        ),
        pack(
            "x3", "Pack Papa Noel", "navidad", "Navidad", R.drawable.ic_gift,
            "Una sorpresa llegada directo del Polo Norte.",
            listOf("Minitorta", "Dulces navideños", "Chocolate", "Adorno", "Tarjeta")
        ),

        // Pascuas
        pack(
            "s1", "Pack Huevitos", "pascuas", "Pascuas", R.drawable.ic_candy,
            "Dulzura para la búsqueda de Pascuas.",
            listOf("Minitorta", "Chocolate", "Golosinas", "Adorno", "Tarjeta")
        ),
        pack(
            "s2", "Pack Primavera", "pascuas", "Pascuas", R.drawable.ic_cake,
            "Un pack fresco y dulce para Pascuas.",
            listOf("Minitorta", "Chocolate", "Golosinas", "Vela", "Tarjeta")
        ),
        pack(
            "s3", "Pack Pascuas Dulces", "pascuas", "Pascuas", R.drawable.ic_sticker,
            "Para endulzar las Pascuas.",
            listOf("Minitorta", "Chocolate", "Golosinas", "Stickers", "Tarjeta")
        ),

        // Día del Niño
        pack(
            "d1", "Pack Diversión", "dianino", "Día del Niño", R.drawable.ic_balloon,
            "Un pack lleno de color para los más chicos.",
            listOf("Minitorta", "Golosinas", "Stickers", "Globo", "Tarjeta")
        ),
        pack(
            "d2", "Pack Pequeñas Sorpresas", "dianino", "Día del Niño", R.drawable.ic_snacks,
            "Sorpresas dulces para festejar su día.",
            listOf("Minitorta", "Golosinas", "Snacks", "Stickers", "Tarjeta")
        ),
        pack(
            "d3", "Pack Juguemos", "dianino", "Día del Niño", R.drawable.ic_gift,
            "Juegos y dulces para una tarde divertida.",
            listOf("Minitorta", "Golosinas", "Globo", "Stickers", "Tarjeta")
        )
    )

    fun packsForOccasion(occasionId: String): List<Pack> = packs.filter { it.occasionId == occasionId }

    val trendingPacks: List<Pack> = packs.filter { it.isTrending }

    fun packById(id: String): Pack? = packs.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: empty box shapes
    // ---------------------------------------------------------------------------------------
    val boxShapes: List<BoxShape> = listOf(
        BoxShape("heart", "Caja corazón", R.drawable.box_heart),
        BoxShape("basket", "Canasta", R.drawable.box_basket),
        BoxShape("rect", "Caja rectangular", R.drawable.box_rect),
        BoxShape("square", "Caja cuadrada", R.drawable.box_square),
        BoxShape("circle", "Caja circular", R.drawable.box_circle),
        BoxShape("briefcase", "Caja tipo maletín", R.drawable.box_briefcase)
    )

    // ---------------------------------------------------------------------------------------
    // Create flow: mini cakes
    // ---------------------------------------------------------------------------------------
    val miniCakes: List<MiniCake> = listOf(
        MiniCake("mc_choco", "Chocolate", R.drawable.ic_cake),
        MiniCake("mc_vainilla", "Vainilla", R.drawable.ic_cake),
        MiniCake("mc_redvelvet", "Red Velvet", R.drawable.ic_cake),
        MiniCake("mc_lemon", "Lemon", R.drawable.ic_cake),
        MiniCake("mc_oreo", "Oreo", R.drawable.ic_cake),
        MiniCake("mc_frutilla", "Frutilla", R.drawable.ic_cake)
    )

    fun miniCakeById(id: String?): MiniCake? = miniCakes.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: additional products.
    // Additionals are included in the pack price; only a few add a surcharge of $1.000.
    // ---------------------------------------------------------------------------------------
    val additionalProducts: List<Product> = listOf(
        Product("ad_choco", "Chocolate", 0, R.drawable.ic_chocolate),
        Product("ad_golosinas", "Golosinas", 0, R.drawable.ic_candy),
        Product("ad_vela", "Vela", 0, R.drawable.ic_candle),
        Product("ad_taza", "Taza", EXTRA_PRODUCT_SURCHARGE, R.drawable.ic_cup),
        Product("ad_pulsera", "Pulsera", 0, R.drawable.ic_bracelet),
        Product("ad_llavero", "Llavero", EXTRA_PRODUCT_SURCHARGE, R.drawable.ic_keychain),
        Product("ad_snacks", "Snacks", 0, R.drawable.ic_snacks),
        Product("ad_carta", "Carta", 0, R.drawable.ic_letter),
        Product("ad_stickers", "Stickers", 0, R.drawable.ic_sticker),
        Product("ad_globo", "Globo", 0, R.drawable.ic_balloon),
        Product("ad_lapicera", "Lapicera", 0, R.drawable.ic_pen),
        Product("ad_peluche", "Peluche", EXTRA_PRODUCT_SURCHARGE, R.drawable.ic_gift),
        Product("ad_adorno", "Adorno", EXTRA_PRODUCT_SURCHARGE, R.drawable.ic_ornament),
        Product("ad_tarjeta", "Tarjeta", 0, R.drawable.ic_card),
        Product("ad_dulces", "Dulces navideños", 0, R.drawable.ic_candy)
    )

    fun productById(id: String): Product? = additionalProducts.firstOrNull { it.id == id }

    fun occasionById(id: String?): Occasion? = createOccasions.firstOrNull { it.id == id }

    fun boxById(id: String?): BoxShape? = boxShapes.firstOrNull { it.id == id }
}
