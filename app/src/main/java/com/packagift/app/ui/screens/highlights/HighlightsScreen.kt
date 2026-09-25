package com.packagift.app.ui.screens.highlights

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.packagift.app.data.model.Pack
import com.packagift.app.ui.components.CarouselSection
import com.packagift.app.ui.components.CartIconButton
import com.packagift.app.ui.theme.Ahsing
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel

@Composable
fun HighlightsScreen(
    viewModel: PackAGiftViewModel,
    onPackClick: (Pack) -> Unit,
    onCartClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Ahsing logo/title. It is part of the scrolling content, so it disappears on scroll.
            item(key = "title") {
                Text(
                    text = "Pack-A-Gift",
                    fontFamily = Ahsing,
                    fontSize = 42.sp,
                    color = WineSecondary,
                    modifier = Modifier.padding(start = 16.dp, end = 70.dp, top = 14.dp, bottom = 2.dp)
                )
            }

            // Packs preparados del Excel: siempre al principio, en Destacados.
            item(key = "nuestros-packs") {
                CarouselSection(
                    title = "Destacados",
                    packs = viewModel.trendingPacks,
                    onPackClick = onPackClick
                )
            }

            items(viewModel.occasions, key = { it.id }) { occasion ->
                CarouselSection(
                    title = occasion.name,
                    packs = viewModel.packsForOccasion(occasion.id),
                    onPackClick = onPackClick
                )
            }
        }

        CartIconButton(
            count = viewModel.cartCount,
            onClick = onCartClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp)
        )
    }
}
