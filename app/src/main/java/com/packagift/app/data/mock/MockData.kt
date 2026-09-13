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
        Occasion("primavera", "Día de la Primavera", R.drawable.ic_candy),
        Occasion("dulzura", "Semana de la Dulzura", R.drawable.ic_chocolate),
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
            "m1", "Pack Abrazo de Mamá", "madre", "Día de la Madre", R.drawable.pack_madre_1,
            "Un regalo dulce pensado para agradecer a mamá en su día.",
            listOf("Minitorta", "Taza", "Chocolate", "Vela", "Tarjeta"), trending = true
        ),
        pack(
            "m2", "Pack Flores y Dulzura", "madre", "Día de la Madre", R.drawable.pack_madre_2,
            "Detalles delicados para sorprender a mamá.",
            listOf("Minitorta", "Chocolate", "Vela aromática", "Tarjeta", "Taza")
        ),
        pack(
            "m3", "Pack Mañana de Mamá", "madre", "Día de la Madre", R.drawable.pack_madre_3,
            "Un desayuno dulce para empezar su día especial.",
            listOf("Minitorta", "Taza", "Snacks", "Chocolate", "Tarjeta")
        ),

        // Día del Padre
        pack(
            "p1", "Pack Para Papá", "padre", "Día del Padre", R.drawable.pack_padre_1,
            "El combo clásico para festejar a papá.",
            listOf("Minitorta", "Taza", "Chocolate", "Llavero", "Tarjeta"), trending = true
        ),
        pack(
            "p2", "Pack Cracks", "padre", "Día del Padre", R.drawable.pack_padre_2,
            "Snacks y dulces para compartir con papá.",
            listOf("Minitorta", "Snacks", "Chocolate", "Llavero", "Tarjeta")
        ),
        pack(
            "p3", "Pack Festejo de Papá", "padre", "Día del Padre", R.drawable.pack_padre_3,
            "Un detalle completo para su día.",
            listOf("Minitorta", "Chocolate", "Snacks", "Llavero", "Tarjeta")
        ),

        // Día del Amigo
        pack(
            "a1", "Pack Bestie", "amigo", "Día del Amigo", R.drawable.pack_amigo_1,
            "Para esa amistad que lo es todo.",
            listOf("Minitorta", "Snacks", "Chocolates", "Pulsera", "Tarjeta"), trending = true
        ),
        pack(
            "a2", "Pack Noche de Amigos", "amigo", "Día del Amigo", R.drawable.pack_amigo_2,
            "Todo listo para una juntada inolvidable.",
            listOf("Minitorta", "Snacks", "Chocolates", "Stickers", "Tarjeta")
        ),
        pack(
            "a3", "Pack Recuerdo", "amigo", "Día del Amigo", R.drawable.pack_amigo_3,
            "Un recuerdo dulce para una amistad especial.",
            listOf("Minitorta", "Chocolate", "Pulsera", "Carta", "Tarjeta")
        ),

        // San Valentín
        pack(
            "v1", "Pack Amor Eterno", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_1,
            "Una declaración dulce para tu persona especial.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Peluche"), trending = true
        ),
        pack(
            "v2", "Pack Corazón", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_2,
            "El clásico romántico que nunca falla.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Pulsera")
        ),
        pack(
            "v3", "Pack Cita Perfecta", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_3,
            "Para una noche especial en pareja.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Detalle")
        ),

        // Aniversario
        pack(
            "n1", "Pack Nuestro Aniversario", "aniversario", "Aniversario", R.drawable.pack_aniversario_1,
            "Celebrá el tiempo compartido con un detalle único.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Detalle"), trending = true
        ),
        pack(
            "n2", "Pack Para Recordar", "aniversario", "Aniversario", R.drawable.pack_aniversario_2,
            "Un recuerdo dulce de un momento especial.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Pulsera")
        ),
        pack(
            "n3", "Pack Años Juntos", "aniversario", "Aniversario", R.drawable.pack_aniversario_3,
            "Para festejar los años compartidos.",
            listOf("Minitorta", "Chocolates", "Vela", "Carta", "Taza")
        ),

        // Cumpleaños
        pack(
            "c1", "Pack Sorpresa", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_1,
            "La sorpresa ideal para un cumpleaños.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Tarjeta"), trending = true
        ),
        pack(
            "c2", "Pack Fiesta", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_2,
            "Todo para armar la fiesta perfecta.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Stickers")
        ),
        pack(
            "c3", "Pack Cumple Feliz", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_3,
            "Un detalle alegre para decir feliz cumpleaños.",
            listOf("Minitorta", "Golosinas", "Vela", "Globo", "Tarjeta")
        ),

        // Graduación
        pack(
            "g1", "Pack Egresado", "graduacion", "Graduación", R.drawable.pack_graduacion_1,
            "Para celebrar un gran logro.",
            listOf("Minitorta", "Chocolates", "Taza", "Lapicera", "Tarjeta"), trending = true
        ),
        pack(
            "g2", "Pack Futuro Brillante", "graduacion", "Graduación", R.drawable.pack_graduacion_2,
            "Un empujón dulce hacia lo que viene.",
            listOf("Minitorta", "Chocolates", "Lapicera", "Stickers", "Tarjeta")
        ),
        pack(
            "g3", "Pack Logro", "graduacion", "Graduación", R.drawable.pack_graduacion_3,
            "Para festejar el esfuerzo y el logro.",
            listOf("Minitorta", "Chocolates", "Taza", "Lapicera", "Tarjeta")
        ),

        // Día de la Primavera
        pack(
            "pr1", "Pack Primavera", "primavera", "Día de la Primavera", R.drawable.pack_primavera_1,
            "Colores y dulzura para celebrar la primavera.",
            listOf("Minitorta", "Chocolate", "Golosinas", "Vela", "Tarjeta")
        ),
        pack(
            "pr2", "Pack Flores de Primavera", "primavera", "Día de la Primavera", R.drawable.pack_primavera_2,
            "Un detalle fresco para dar la bienvenida a la primavera.",
            listOf("Minitorta", "Chocolate", "Vela", "Tarjeta", "Pulsera")
        ),
        pack(
            "pr3", "Pack Día de la Primavera", "primavera", "Día de la Primavera", R.drawable.pack_primavera_3,
            "Un pack alegre para festejar el 21 de septiembre.",
            listOf("Minitorta", "Snacks", "Chocolates", "Stickers", "Tarjeta")
        ),

        // Semana de la Dulzura
        pack(
            "du1", "Pack Dulzura", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_1,
            "Para endulzar cada día de la semana de la dulzura.",
            listOf("Minitorta", "Golosinas", "Chocolate", "Vela", "Tarjeta")
        ),
        pack(
            "du2", "Pack Dulce Semana", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_2,
            "Todo el dulce que alguien especial merece.",
            listOf("Minitorta", "Chocolates", "Golosinas", "Stickers", "Tarjeta")
        ),
        pack(
            "du3", "Pack Para Endulzar", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_3,
            "Un regalo dulce para compartir.",
            listOf("Minitorta", "Chocolate", "Golosinas", "Taza", "Tarjeta")
        ),

        // Día del Niño
        pack(
            "d1", "Pack Diversión", "dianino", "Día del Niño", R.drawable.pack_dianino_1,
            "Un pack lleno de color para los más chicos.",
            listOf("Minitorta", "Golosinas", "Stickers", "Globo", "Tarjeta")
        ),
        pack(
            "d2", "Pack Pequeñas Sorpresas", "dianino", "Día del Niño", R.drawable.pack_dianino_2,
            "Sorpresas dulces para festejar su día.",
            listOf("Minitorta", "Golosinas", "Snacks", "Stickers", "Tarjeta")
        ),
        pack(
            "d3", "Pack Juguemos", "dianino", "Día del Niño", R.drawable.pack_dianino_3,
            "Juegos y dulces para una tarde divertida.",
            listOf("Minitorta", "Golosinas", "Globo", "Stickers", "Tarjeta")
        )
    )

    fun packsForOccasion(occasionId: String): List<Pack> = packs.filter { it.occasionId == occasionId }

    val trendingPacks: List<Pack> = packs.filter { it.isTrending }

    fun packById(id: String): Pack? = packs.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: empty box shapes (from the provided images)
    // ---------------------------------------------------------------------------------------
    val boxShapes: List<BoxShape> = listOf(
        BoxShape("box1", "Caja rectangular", R.drawable.box_1),
        BoxShape("box2", "Caja con ventana", R.drawable.box_2),
        BoxShape("box3", "Caja corazón", R.drawable.box_3),
        BoxShape("box4", "Bandeja", R.drawable.box_4),
        BoxShape("box5", "Caja cuadrada", R.drawable.box_5),
        BoxShape("box6", "Caja con moño", R.drawable.box_6)
    )

    // ---------------------------------------------------------------------------------------
    // Create flow: mini cakes (from the provided images)
    // ---------------------------------------------------------------------------------------
    val miniCakes: List<MiniCake> = listOf(
        MiniCake("cake_chocotorta", "Chocotorta", R.drawable.cake_chocotorta),
        MiniCake("cake_frutilla", "Frutilla", R.drawable.cake_frutilla),
        MiniCake("cake_redvelvet", "Red Velvet", R.drawable.cake_redvelvet),
        MiniCake("cake_lemon", "Lemon", R.drawable.cake_lemon),
        MiniCake("cake_oreo", "Oreo", R.drawable.cake_oreo),
        MiniCake("cake_rocklets", "Rocklets", R.drawable.cake_rocklets)
    )

    fun miniCakeById(id: String?): MiniCake? = miniCakes.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: additional products (from the provided images).
    // Additionals are included in the pack price; only taza and peluche add $1.000.
    // ---------------------------------------------------------------------------------------
    val additionalProducts: List<Product> = listOf(
        Product("add_block", "Block", 0, R.drawable.add_block),
        Product("add_bonobon", "Bon o Bon", 0, R.drawable.add_bonobon),
        Product("add_buttertoffees", "Butter Toffees", 0, R.drawable.add_buttertoffees),
        Product("add_cadbury", "Cadbury", 0, R.drawable.add_cadbury),
        Product("add_cofler", "Cofler", 0, R.drawable.add_cofler),
        Product("add_ferrero", "Ferrero Rocher", 0, R.drawable.add_ferrero),
        Product("add_franui", "Franui", 0, R.drawable.add_franui),
        Product("add_kinder", "Kinder", 0, R.drawable.add_kinder),
        Product("add_kitkat", "KitKat", 0, R.drawable.add_kitkat),
        Product("add_milka", "Milka", 0, R.drawable.add_milka),
        Product("add_mogul", "Mogul", 0, R.drawable.add_mogul),
        Product("add_rocklets", "Rocklets", 0, R.drawable.add_rocklets),
        Product("add_peluche", "Peluche", EXTRA_PRODUCT_SURCHARGE, R.drawable.add_peluche),
        Product("add_taza", "Taza personalizada", EXTRA_PRODUCT_SURCHARGE, R.drawable.add_taza),
        Product("add_tarjeta", "Tarjeta", 0, R.drawable.add_tarjeta)
    )

    fun productById(id: String): Product? = additionalProducts.firstOrNull { it.id == id }

    fun occasionById(id: String?): Occasion? = createOccasions.firstOrNull { it.id == id }

    fun boxById(id: String?): BoxShape? = boxShapes.firstOrNull { it.id == id }
}
