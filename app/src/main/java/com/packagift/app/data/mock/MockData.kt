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
 * Precios reales según Excel del emprendimiento (precios de venta finales redondeados):
 * - Pack Abrazo de Mamá: $35.000
 * - Pack Cumple Feliz: $34.500
 * - Pack Aniversario: $13.500
 * - Pack Día del Niño: $28.000
 * - Personalizado Simple: $27.500 / Intermedio: $34.000 / Grande: $37.500
 *   (ver PackageSize.basePrice en Models.kt)
 *
 * Los 4 packs prearmados del Excel van siempre al principio (trendingPacks).
 * No se venden flores: se eliminaron los packs con flores y todos los items usan
 * solo productos reales del Excel.
 */
object MockData {

    // ----------------------------------------------------------------------------------
    // Precios reales de venta (Excel - "Precio de Venta Final Redondeado")
    // ----------------------------------------------------------------------------------
    const val PRICE_ABRAZO_MAMA = 35000
    const val PRICE_CUMPLE_FELIZ = 34500
    const val PRICE_ANIVERSARIO = 13500
    const val PRICE_DIA_NINO = 28000

    /**
     * Adicionales del Excel ("Precio adicional para pack personalizado"):
     * casi todo está incluido en el precio base ($0). Solo 3 premium suman extra:
     * Peluche +$8.300, Taza personalizada +$5.300, Franui +$2.200.
     */
    const val EXTRA_PELUCHE = 8300
    const val EXTRA_TAZA = 5300
    const val EXTRA_FRANUI = 2200

    val user = User(
        id = "u1",
        name = "Usuario 1",
        avatarRes = R.drawable.ic_avatar
    )

    val logoRes: Int = R.drawable.ic_gift

    // ---------------------------------------------------------------------------------------
    // Occasions - los 4 packs preparados primero (madre, cumpleaños, aniversario, día del niño)
    // ---------------------------------------------------------------------------------------
    val occasions: List<Occasion> = listOf(
        Occasion("madre", "Día de la Madre", R.drawable.ic_card),
        Occasion("cumpleanos", "Cumpleaños", R.drawable.ic_balloon),
        Occasion("aniversario", "Aniversario", R.drawable.ic_letter),
        Occasion("dianino", "Día del Niño", R.drawable.ic_balloon),
        Occasion("padre", "Día del Padre", R.drawable.ic_cup),
        Occasion("amigo", "Día del Amigo", R.drawable.ic_bracelet),
        Occasion("sanvalentin", "San Valentín", R.drawable.ic_card),
        Occasion("graduacion", "Graduación", R.drawable.ic_pen),
        Occasion("primavera", "Día de la Primavera", R.drawable.ic_candy),
        Occasion("dulzura", "Semana de la Dulzura", R.drawable.ic_chocolate)
    )

    /** Extra option offered in the Create flow. */
    val otherOccasion = Occasion("otra", "Otra ocasión", R.drawable.ic_gift)

    val createOccasions: List<Occasion> = occasions + otherOccasion

    // ---------------------------------------------------------------------------------------
    // Packs (pre-made, always tied to an occasion).
    // Los 4 primeros son los packs preparados del Excel, con composición y precio reales.
    // El resto son variaciones por ocasión usando SOLO productos reales del Excel
    // (sin flores, sin globos, sin pulseras, sin stickers: Mogul, Milka, KitKat, Kinder,
    //  Cofler, Cadbury, Bon o Bon, Butter Toffees, Rocklets, Block, Franui, Ferrero,
    //  Peluche, Taza personalizada, Tarjeta, Velita, Mini Chocotorta, Mini Lemon Pie).
    // ---------------------------------------------------------------------------------------
    private fun pack(
        id: String,
        name: String,
        occasionId: String,
        occasionName: String,
        imageRes: Int,
        price: Int,
        description: String,
        items: List<String>,
        trending: Boolean = false
    ) = Pack(
        id = id,
        name = name,
        occasionId = occasionId,
        occasionName = occasionName,
        description = description,
        price = price,
        imageRes = imageRes,
        items = items,
        isTrending = trending
    )

    val packs: List<Pack> = listOf(
        // ---- NUESTROS PACKS PREPARADOS (Excel, siempre primeros) ----
        pack(
            "m1", "Pack Abrazo de Mamá", "madre", "Día de la Madre", R.drawable.pack_madre_1,
            PRICE_ABRAZO_MAMA,
            "Caja corazón con peluche, Milka, Mogul, Mini Chocotorta y tarjeta.",
            listOf("Caja corazón", "Peluche", "Milka", "Mogul", "Mini Chocotorta", "Tarjeta"),
            trending = true
        ),
        pack(
            "c3", "Pack Cumple Feliz", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_3,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Cofler, Rocklets, Mini Chocotorta, velita, taza personalizada y tarjeta.",
            listOf(
                "Bandeja", "Cofler", "Rocklets", "Tarjeta", "Mini Chocotorta",
                "Velita", "Taza personalizada"
            ),
            trending = true
        ),
        pack(
            "n1", "Pack Aniversario", "aniversario", "Aniversario", R.drawable.pack_aniversario_1,
            PRICE_ANIVERSARIO,
            "Caja con ventana con Butter Toffees, Cadbury, Bon o Bon, Mini Lemon Pie y tarjeta.",
            listOf(
                "Caja con ventana", "Butter Toffees x10", "Cadbury", "Bon o Bon",
                "Mini Lemon Pie", "Tarjeta"
            ),
            trending = true
        ),
        pack(
            "d1", "Pack Día del Niño", "dianino", "Día del Niño", R.drawable.pack_dianino_1,
            PRICE_DIA_NINO,
            "Caja con moño con Kinder, Mogul, Rocklets, Mini Chocotorta y Block.",
            listOf(
                "Caja con moño", "Kinder", "Mogul", "Rocklets", "Mini Chocotorta", "Block"
            ),
            trending = true
        ),

        // ---- Variaciones por ocasión (solo productos reales del Excel) ----
        // Día de la Madre
        pack(
            "m2", "Pack Dulzura de Mamá", "madre", "Día de la Madre", R.drawable.pack_madre_2,
            PRICE_ABRAZO_MAMA,
            "Caja corazón con Ferrero Rocher, Milka, Mini Lemon Pie y tarjeta.",
            listOf("Caja corazón", "Ferrero Rocher", "Milka", "Mini Lemon Pie", "Tarjeta")
        ),
        pack(
            "m3", "Pack Mañana de Mamá", "madre", "Día de la Madre", R.drawable.pack_madre_3,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Mini Chocotorta, taza personalizada, Bon o Bon y tarjeta.",
            listOf("Bandeja", "Mini Chocotorta", "Taza personalizada", "Bon o Bon", "Tarjeta")
        ),

        // Cumpleaños
        pack(
            "c1", "Pack Sorpresa", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_1,
            PRICE_DIA_NINO,
            "Caja cuadrada con Mini Chocotorta, KitKat, Mogul, velita y tarjeta.",
            listOf("Caja cuadrada", "Mini Chocotorta", "KitKat", "Mogul", "Velita", "Tarjeta")
        ),
        pack(
            "c2", "Pack Fiesta", "cumpleanos", "Cumpleaños", R.drawable.pack_cumpleanos_2,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Cofler, Rocklets, Franui, velita y tarjeta.",
            listOf("Bandeja", "Cofler", "Rocklets", "Franui", "Velita", "Tarjeta")
        ),

        // Aniversario
        pack(
            "n2", "Pack Para Recordar", "aniversario", "Aniversario", R.drawable.pack_aniversario_2,
            PRICE_ANIVERSARIO,
            "Caja con ventana con Butter Toffees, Milka, Mini Lemon Pie y tarjeta.",
            listOf("Caja con ventana", "Butter Toffees x10", "Milka", "Mini Lemon Pie", "Tarjeta")
        ),
        pack(
            "n3", "Pack Años Juntos", "aniversario", "Aniversario", R.drawable.pack_aniversario_3,
            PRICE_CUMPLE_FELIZ,
            "Caja rectangular con Mini Chocotorta, Bon o Bon, taza personalizada y tarjeta.",
            listOf("Caja rectangular", "Mini Chocotorta", "Bon o Bon", "Taza personalizada", "Tarjeta")
        ),

        // Día del Niño
        pack(
            "d2", "Pack Pequeñas Sorpresas", "dianino", "Día del Niño", R.drawable.pack_dianino_2,
            PRICE_DIA_NINO,
            "Caja con moño con Block, Kinder, Butter Toffees y tarjeta.",
            listOf("Caja con moño", "Block", "Kinder", "Butter Toffees x10", "Tarjeta")
        ),
        pack(
            "d3", "Pack Juguemos", "dianino", "Día del Niño", R.drawable.pack_dianino_3,
            PRICE_DIA_NINO,
            "Caja cuadrada con Cofler, Mogul, Rocklets, Mini Lemon Pie y tarjeta.",
            listOf("Caja cuadrada", "Cofler", "Mogul", "Rocklets", "Mini Lemon Pie", "Tarjeta")
        ),

        // Día del Padre
        pack(
            "p1", "Pack Para Papá", "padre", "Día del Padre", R.drawable.pack_padre_1,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Mini Chocotorta, taza personalizada, Cofler y tarjeta.",
            listOf("Bandeja", "Mini Chocotorta", "Taza personalizada", "Cofler", "Tarjeta")
        ),
        pack(
            "p2", "Pack Cracks", "padre", "Día del Padre", R.drawable.pack_padre_2,
            PRICE_DIA_NINO,
            "Caja rectangular con Block, KitKat, Mogul y tarjeta.",
            listOf("Caja rectangular", "Block", "KitKat", "Mogul", "Tarjeta")
        ),
        pack(
            "p3", "Pack Festejo de Papá", "padre", "Día del Padre", R.drawable.pack_padre_3,
            PRICE_DIA_NINO,
            "Caja cuadrada con Mini Chocotorta, Cadbury, Rocklets y tarjeta.",
            listOf("Caja cuadrada", "Mini Chocotorta", "Cadbury", "Rocklets", "Tarjeta")
        ),

        // Día del Amigo
        pack(
            "a1", "Pack Bestie", "amigo", "Día del Amigo", R.drawable.pack_amigo_1,
            PRICE_DIA_NINO,
            "Caja cuadrada con Mini Chocotorta, Milka, Mogul, Rocklets y tarjeta.",
            listOf("Caja cuadrada", "Mini Chocotorta", "Milka", "Mogul", "Rocklets", "Tarjeta")
        ),
        pack(
            "a2", "Pack Noche de Amigos", "amigo", "Día del Amigo", R.drawable.pack_amigo_2,
            PRICE_ANIVERSARIO,
            "Caja con ventana con Butter Toffees, Cadbury, Bon o Bon y tarjeta.",
            listOf("Caja con ventana", "Butter Toffees x10", "Cadbury", "Bon o Bon", "Tarjeta")
        ),
        pack(
            "a3", "Pack Recuerdo", "amigo", "Día del Amigo", R.drawable.pack_amigo_3,
            PRICE_DIA_NINO,
            "Caja rectangular con Mini Lemon Pie, KitKat, Cofler y tarjeta.",
            listOf("Caja rectangular", "Mini Lemon Pie", "KitKat", "Cofler", "Tarjeta")
        ),

        // San Valentín
        pack(
            "v1", "Pack Amor Eterno", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_1,
            PRICE_ABRAZO_MAMA,
            "Caja corazón con peluche, Ferrero Rocher, Mini Chocotorta y tarjeta.",
            listOf("Caja corazón", "Peluche", "Ferrero Rocher", "Mini Chocotorta", "Tarjeta")
        ),
        pack(
            "v2", "Pack Corazón", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_2,
            PRICE_ANIVERSARIO,
            "Caja corazón con Bon o Bon, Milka, Mini Lemon Pie y tarjeta.",
            listOf("Caja corazón", "Bon o Bon", "Milka", "Mini Lemon Pie", "Tarjeta")
        ),
        pack(
            "v3", "Pack Cita Perfecta", "sanvalentin", "San Valentín", R.drawable.pack_sanvalentin_3,
            PRICE_DIA_NINO,
            "Caja rectangular con Mini Chocotorta, Franui, Kinder y tarjeta.",
            listOf("Caja rectangular", "Mini Chocotorta", "Franui", "Kinder", "Tarjeta")
        ),

        // Graduación
        pack(
            "g1", "Pack Egresado", "graduacion", "Graduación", R.drawable.pack_graduacion_1,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Mini Chocotorta, taza personalizada, KitKat y tarjeta.",
            listOf("Bandeja", "Mini Chocotorta", "Taza personalizada", "KitKat", "Tarjeta")
        ),
        pack(
            "g2", "Pack Futuro Brillante", "graduacion", "Graduación", R.drawable.pack_graduacion_2,
            PRICE_DIA_NINO,
            "Caja cuadrada con Cofler, Franui, Mogul y tarjeta.",
            listOf("Caja cuadrada", "Cofler", "Franui", "Mogul", "Tarjeta")
        ),
        pack(
            "g3", "Pack Logro", "graduacion", "Graduación", R.drawable.pack_graduacion_3,
            PRICE_DIA_NINO,
            "Caja rectangular con Block, Cadbury, Mini Lemon Pie y tarjeta.",
            listOf("Caja rectangular", "Block", "Cadbury", "Mini Lemon Pie", "Tarjeta")
        ),

        // Día de la Primavera
        pack(
            "pr1", "Pack Primavera", "primavera", "Día de la Primavera", R.drawable.pack_primavera_1,
            PRICE_ANIVERSARIO,
            "Caja con ventana con Butter Toffees, Mogul, Mini Lemon Pie y tarjeta.",
            listOf("Caja con ventana", "Butter Toffees x10", "Mogul", "Mini Lemon Pie", "Tarjeta")
        ),
        pack(
            "pr3", "Pack Día de la Primavera", "primavera", "Día de la Primavera", R.drawable.pack_primavera_3,
            PRICE_DIA_NINO,
            "Caja cuadrada con Mini Chocotorta, KitKat, Bon o Bon y tarjeta.",
            listOf("Caja cuadrada", "Mini Chocotorta", "KitKat", "Bon o Bon", "Tarjeta")
        ),

        // Semana de la Dulzura
        pack(
            "du1", "Pack Dulzura", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_1,
            PRICE_ANIVERSARIO,
            "Caja con ventana con Butter Toffees, Cadbury, Mogul y tarjeta.",
            listOf("Caja con ventana", "Butter Toffees x10", "Cadbury", "Mogul", "Tarjeta")
        ),
        pack(
            "du2", "Pack Dulce Semana", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_2,
            PRICE_DIA_NINO,
            "Caja rectangular con Franui, Milka, Kinder, Rocklets y tarjeta.",
            listOf("Caja rectangular", "Franui", "Milka", "Kinder", "Rocklets", "Tarjeta")
        ),
        pack(
            "du3", "Pack Para Endulzar", "dulzura", "Semana de la Dulzura", R.drawable.pack_dulzura_3,
            PRICE_CUMPLE_FELIZ,
            "Bandeja con Mini Chocotorta, Cofler, taza personalizada y tarjeta.",
            listOf("Bandeja", "Mini Chocotorta", "Cofler", "Taza personalizada", "Tarjeta")
        )
    )

    fun packsForOccasion(occasionId: String): List<Pack> = packs.filter { it.occasionId == occasionId }

    /** Los 4 packs preparados del Excel, siempre primeros. */
    val trendingPacks: List<Pack> = packs.filter { it.isTrending }

    fun packById(id: String): Pack? = packs.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: empty box shapes (fotos reales de cajas del emprendimiento)
    // ---------------------------------------------------------------------------------------
    val boxShapes: List<BoxShape> = listOf(
        BoxShape("box1", "Caja rectangular", R.drawable.box_real_1),
        BoxShape("box2", "Caja con ventana", R.drawable.box_real_2),
        BoxShape("box3", "Caja corazón", R.drawable.box_real_3),
        BoxShape("box4", "Bandeja", R.drawable.box_real_4),
        BoxShape("box5", "Caja cuadrada", R.drawable.box_real_5),
        BoxShape("box6", "Caja con moño", R.drawable.box_real_6)
    )

    // ---------------------------------------------------------------------------------------
    // Create flow: mini cakes (fotos reales de minitortas del emprendimiento)
    // ---------------------------------------------------------------------------------------
    val miniCakes: List<MiniCake> = listOf(
        MiniCake("cake_chocotorta", "Chocotorta", R.drawable.cake_real_chocotorta),
        MiniCake("cake_lemon", "Lemon Pie", R.drawable.cake_real_lemon),
        MiniCake("cake_frutilla", "Frutilla", R.drawable.cake_real_frutilla),
        MiniCake("cake_oreo", "Oreo", R.drawable.cake_real_oreo),
        MiniCake("cake_redvelvet", "Red Velvet", R.drawable.cake_real_redvelvet),
        MiniCake("cake_rocklets", "Rocklets", R.drawable.cake_real_rocklets)
    )

    fun miniCakeById(id: String?): MiniCake? = miniCakes.firstOrNull { it.id == id }

    // ---------------------------------------------------------------------------------------
    // Create flow: additional products (iguales al Excel, con fotos reales).
    // Incluidos en el precio base salvo 3 premium: Peluche +$8.300,
    // Taza personalizada +$5.300, Franui +$2.200.
    // ---------------------------------------------------------------------------------------
    val additionalProducts: List<Product> = listOf(
        Product("add_block", "Block", 0, R.drawable.prod_block),
        Product("add_bonobon", "Bon o Bon", 0, R.drawable.prod_bonobon),
        Product("add_buttertoffees", "Butter Toffees", 0, R.drawable.prod_buttertoffees),
        Product("add_cadbury", "Cadbury", 0, R.drawable.prod_cadbury),
        Product("add_cofler", "Cofler", 0, R.drawable.prod_cofler),
        Product("add_ferrero", "Ferrero Rocher", 0, R.drawable.prod_ferrero),
        Product("add_franui", "Franui", EXTRA_FRANUI, R.drawable.prod_franui),
        Product("add_kinder", "Kinder", 0, R.drawable.prod_kinder),
        Product("add_kitkat", "KitKat", 0, R.drawable.prod_kitkat),
        Product("add_milka", "Milka", 0, R.drawable.prod_milka),
        Product("add_mogul", "Mogul", 0, R.drawable.prod_mogul),
        Product("add_rocklets", "Rocklets", 0, R.drawable.prod_rocklets),
        Product("add_peluche", "Peluche", EXTRA_PELUCHE, R.drawable.prod_peluche),
        Product("add_taza", "Taza personalizada", EXTRA_TAZA, R.drawable.prod_taza),
        Product("add_tarjeta", "Tarjeta", 0, R.drawable.prod_tarjeta),
        Product("add_velita", "Velita", 0, R.drawable.ic_candle)
    )

    fun productById(id: String): Product? = additionalProducts.firstOrNull { it.id == id }

    fun occasionById(id: String?): Occasion? = createOccasions.firstOrNull { it.id == id }

    fun boxById(id: String?): BoxShape? = boxShapes.firstOrNull { it.id == id }
}
