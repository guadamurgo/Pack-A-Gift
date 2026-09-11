package com.packagift.app.ui.screens.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.packagift.app.ui.components.RoundIconButton
import com.packagift.app.ui.components.formatPrice
import com.packagift.app.ui.theme.BlueLight
import com.packagift.app.ui.theme.PinkPrimary
import com.packagift.app.ui.theme.TextPrimary
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel
import kotlinx.coroutines.launch

@Composable
fun PackDetailScreen(
    viewModel: PackAGiftViewModel,
    packId: String?,
    onBack: () -> Unit,
    onCartClick: () -> Unit
) {
    val pack = packId?.let { viewModel.packById(it) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (pack == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Pack no encontrado", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    val isFavorite = viewModel.isFavorite(pack.id)

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.1f)
                    .background(BlueLight.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(pack.imageRes),
                    contentDescription = pack.name,
                    modifier = Modifier.fillMaxSize(0.5f)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Surface(
                    color = PinkPrimary.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = pack.occasionName,
                        style = MaterialTheme.typography.labelMedium,
                        color = WineSecondary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(text = pack.name, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(4.dp))
                Text(
                    text = formatPrice(pack.price),
                    style = MaterialTheme.typography.headlineSmall,
                    color = WineSecondary
                )
                Spacer(Modifier.height(14.dp))
                Text(
                    text = pack.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = TextPrimary
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Incluye ${pack.items.size} productos",
                    style = MaterialTheme.typography.titleMedium,
                    color = WineSecondary
                )
                Spacer(Modifier.height(8.dp))
                pack.items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(PinkPrimary)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(text = item, style = MaterialTheme.typography.bodyLarge)
                    }
                }
                Spacer(Modifier.height(110.dp))
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopStart)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RoundIconButton(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Volver",
                onClick = onBack
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                RoundIconButton(
                    icon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Favorito",
                    onClick = { viewModel.toggleFavorite(pack.id) },
                    tint = WineSecondary
                )
                RoundIconButton(
                    icon = Icons.Filled.ShoppingCart,
                    contentDescription = "Carrito",
                    onClick = onCartClick
                )
            }
        }

        Button(
            onClick = {
                viewModel.addPackToCart(pack)
                scope.launch { snackbarHostState.showSnackbar("Agregado al carrito") }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkPrimary,
                contentColor = WineSecondary
            )
        ) {
            Text(text = "Agregar al carrito", style = MaterialTheme.typography.titleMedium)
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 84.dp)
        )
    }
}
