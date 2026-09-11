package com.packagift.app.ui.screens.create

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.packagift.app.data.mock.MockData
import com.packagift.app.data.model.PackageSize
import com.packagift.app.ui.components.SizeOptionCard
import com.packagift.app.ui.components.SquareSelectCard
import com.packagift.app.ui.components.TextOptionCard
import com.packagift.app.ui.components.formatPrice
import com.packagift.app.ui.theme.BackgroundPink
import com.packagift.app.ui.theme.BlueLight
import com.packagift.app.ui.theme.CardBorder
import com.packagift.app.ui.theme.PinkPrimary
import com.packagift.app.ui.theme.TextPrimary
import com.packagift.app.ui.theme.TextSecondary
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel

@Composable
internal fun OccasionStep(viewModel: PackAGiftViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        StepQuestion("¿Para quién o qué ocasión es el regalo?")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.createOccasions, key = { it.id }) { occasion ->
                TextOptionCard(
                    label = occasion.name,
                    selected = viewModel.selectedOccasion?.id == occasion.id,
                    onClick = { viewModel.selectOccasion(occasion) }
                )
            }
        }
    }
}

@Composable
internal fun BoxShapeStep(viewModel: PackAGiftViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        StepQuestion("¿Qué forma de caja querés?")
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.boxShapes, key = { it.id }) { box ->
                SquareSelectCard(
                    imageRes = box.imageRes,
                    label = box.name,
                    selected = viewModel.selectedBox?.id == box.id,
                    onClick = { viewModel.selectBox(box) }
                )
            }
        }
    }
}

@Composable
internal fun SizeStep(viewModel: PackAGiftViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        StepQuestion("¿Qué tamaño querés?")
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PackageSize.entries.forEach { size ->
                SizeOptionCard(
                    size = size,
                    selected = viewModel.selectedSize == size,
                    onClick = { viewModel.selectSize(size) }
                )
            }
        }
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
internal fun CakeStep(viewModel: PackAGiftViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        StepQuestion("Elegí tu minitorta")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.miniCakes, key = { it.id }) { cake ->
                SquareSelectCard(
                    imageRes = cake.imageRes,
                    label = cake.name,
                    selected = viewModel.selectedCake?.id == cake.id,
                    onClick = { viewModel.selectCake(cake) }
                )
            }
        }
    }
}

@Composable
internal fun AdditionalsStep(
    viewModel: PackAGiftViewModel,
    onLimitReached: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
            Text(
                text = "Elegí tus adicionales",
                style = MaterialTheme.typography.titleLarge,
                color = WineSecondary
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${viewModel.selectedAdditionals.size} de ${viewModel.additionalLimit} adicionales seleccionados",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(viewModel.additionalProducts, key = { it.id }) { product ->
                SquareSelectCard(
                    imageRes = product.imageRes,
                    label = product.name,
                    selected = product.id in viewModel.selectedAdditionals,
                    onClick = {
                        if (!viewModel.toggleAdditional(product.id)) onLimitReached()
                    },
                    price = product.price.takeIf { it > 0 }
                )
            }
        }
    }
}

@Composable
internal fun SummaryStep(viewModel: PackAGiftViewModel) {
    val occasion = viewModel.selectedOccasion?.name ?: "-"
    val box = viewModel.selectedBox
    val size = viewModel.selectedSize
    val cake = viewModel.selectedCake?.name ?: "-"
    val total = viewModel.customTotal ?: 0
    val additionalNames = viewModel.selectedAdditionals
        .mapNotNull { MockData.productById(it)?.name }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundPink),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.4f)
                        .background(BlueLight.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (box != null) {
                        Image(
                            painter = painterResource(box.imageRes),
                            contentDescription = box.name,
                            modifier = Modifier.fillMaxSize(0.45f)
                        )
                    }
                }
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Pack personalizado — $occasion",
                        style = MaterialTheme.typography.titleMedium,
                        color = WineSecondary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "${box?.name ?: ""} · Minitorta de $cake + ${viewModel.selectedAdditionals.size} adicionales",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }
        }

        Spacer(Modifier.height(18.dp))
        DetailRow("Ocasión", occasion)
        DetailRow("Forma de la caja", box?.name ?: "-")
        DetailRow(
            "Tamaño",
            size?.let { "${it.label} (1 minitorta + ${it.additionalCount} adicionales)" } ?: "-"
        )
        DetailRow("Minitorta", cake)

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Adicionales (${additionalNames.size})",
            style = MaterialTheme.typography.titleMedium,
            color = WineSecondary
        )
        Spacer(Modifier.height(8.dp))
        if (additionalNames.isEmpty()) {
            Text(
                text = "Sin adicionales",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
        } else {
            additionalNames.forEach { name ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(PinkPrimary)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(text = name, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }

        Spacer(Modifier.height(20.dp))
        Text(
            text = "Comentario para tu pack",
            style = MaterialTheme.typography.titleMedium,
            color = WineSecondary
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = viewModel.customComment,
            onValueChange = { viewModel.updateComment(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ej: Que la tarjeta diga \"Feliz cumple, te quiero\"") },
            minLines = 3,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = WineSecondary,
                unfocusedBorderColor = CardBorder,
                focusedLabelColor = WineSecondary,
                cursorColor = WineSecondary
            )
        )

        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleLarge,
                color = WineSecondary
            )
            Text(
                text = formatPrice(total),
                style = MaterialTheme.typography.titleLarge,
                color = WineSecondary
            )
        }
        Spacer(Modifier.height(20.dp))
    }
}

@Composable
private fun StepQuestion(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        color = WineSecondary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = TextPrimary,
            textAlign = TextAlign.End
        )
    }
}
