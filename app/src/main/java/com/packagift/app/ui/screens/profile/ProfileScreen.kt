package com.packagift.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.packagift.app.data.model.Order
import com.packagift.app.data.model.Pack
import com.packagift.app.ui.components.EmptyState
import com.packagift.app.ui.components.formatDate
import com.packagift.app.ui.components.formatPrice
import com.packagift.app.ui.theme.BackgroundPink
import com.packagift.app.ui.theme.BlueLight
import com.packagift.app.ui.theme.PinkPrimary
import com.packagift.app.ui.theme.TextSecondary
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    viewModel: PackAGiftViewModel,
    onPackClick: (Pack) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item(key = "header") { ProfileHeader(viewModel) }

            item(key = "fav_title") { SectionHeader("Favoritos") }
            val favorites = viewModel.favoritePacks
            if (favorites.isEmpty()) {
                item(key = "fav_empty") {
                    EmptyState("Todavía no guardaste ningún favorito.")
                }
            } else {
                items(favorites, key = { "fav_${it.id}" }) { pack ->
                    FavoriteRow(pack = pack, onClick = { onPackClick(pack) })
                }
            }

            item(key = "orders_title") { SectionHeader("Compras anteriores") }
            val orders = viewModel.orders
            if (orders.isEmpty()) {
                item(key = "orders_empty") {
                    EmptyState("Todavía no realizaste ninguna compra.")
                }
            } else {
                items(orders, key = { it.id }) { order ->
                    OrderRow(
                        order = order,
                        onAddToCart = {
                            viewModel.addOrderToCart(order)
                            scope.launch {
                                snackbarHostState.showSnackbar("Agregado al carrito")
                            }
                        }
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ProfileHeader(viewModel: PackAGiftViewModel) {
    val user = viewModel.user
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(BackgroundPink),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(user.avatarRes),
                contentDescription = user.name,
                modifier = Modifier.fillMaxSize(0.8f)
            )
        }
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Pack-A-Gift",
                style = MaterialTheme.typography.labelLarge,
                color = WineSecondary
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = WineSecondary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
    )
}

@Composable
private fun FavoriteRow(
    pack: Pack,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundPink),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(78.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(BlueLight.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(pack.imageRes),
                    contentDescription = pack.name,
                    modifier = Modifier.fillMaxSize(0.72f)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = pack.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = formatPrice(pack.price),
                    style = MaterialTheme.typography.bodyLarge,
                    color = WineSecondary
                )
            }
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = PinkPrimary
            )
        }
    }
}

@Composable
private fun OrderRow(
    order: Order,
    onAddToCart: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundPink),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(78.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(PinkPrimary.copy(alpha = 0.45f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(order.imageRes),
                        contentDescription = order.title,
                        modifier = Modifier.fillMaxSize(0.72f)
                    )
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = order.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = formatDate(order.dateMillis),
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = order.status.label,
                        style = MaterialTheme.typography.bodySmall,
                        color = WineSecondary
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = formatPrice(order.total),
                    style = MaterialTheme.typography.titleMedium,
                    color = WineSecondary
                )
            }
            Spacer(Modifier.height(10.dp))
            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = WineSecondary
                )
            ) {
                Text(
                    text = "Agregar al carrito",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}
