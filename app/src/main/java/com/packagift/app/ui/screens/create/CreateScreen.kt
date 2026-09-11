package com.packagift.app.ui.screens.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.packagift.app.ui.components.CartIconButton
import com.packagift.app.ui.theme.BlueLight
import com.packagift.app.ui.theme.PinkPrimary
import com.packagift.app.ui.theme.TextSecondary
import com.packagift.app.ui.theme.WineSecondary
import com.packagift.app.ui.viewmodel.PackAGiftViewModel
import kotlinx.coroutines.launch

private val stepTitles = listOf(
    "Temática",
    "Forma de caja",
    "Tamaño",
    "Minitorta",
    "Adicionales",
    "Resumen"
)

@Composable
fun CreateScreen(
    viewModel: PackAGiftViewModel,
    onCartClick: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Crear pack",
                        style = MaterialTheme.typography.titleLarge,
                        color = WineSecondary
                    )
                    Text(
                        text = "Paso ${viewModel.step + 1} de 6 · ${stepTitles[viewModel.step]}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
                CartIconButton(count = viewModel.cartCount, onClick = onCartClick)
            }

            LinearProgressIndicator(
                progress = { (viewModel.step + 1) / 6f },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .height(8.dp)
                    .clip(RoundedCornerShape(50)),
                color = WineSecondary,
                trackColor = BlueLight
            )

            Box(modifier = Modifier.weight(1f)) {
                when (viewModel.step) {
                    0 -> OccasionStep(viewModel)
                    1 -> BoxShapeStep(viewModel)
                    2 -> SizeStep(viewModel)
                    3 -> CakeStep(viewModel)
                    4 -> AdditionalsStep(viewModel) {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Ya alcanzaste el límite. Quitá un adicional para sumar otro."
                            )
                        }
                    }
                    else -> SummaryStep(viewModel)
                }
            }

            CreateBottomBar(
                viewModel = viewModel,
                onAddedToCart = {
                    scope.launch { snackbarHostState.showSnackbar("Agregado al carrito") }
                }
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 84.dp)
        )
    }
}

@Composable
private fun CreateBottomBar(
    viewModel: PackAGiftViewModel,
    onAddedToCart: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (viewModel.step == 5) {
            OutlinedButton(
                onClick = { viewModel.goToStep(0) },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Editar", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                onClick = { if (viewModel.addCustomToCart()) onAddedToCart() },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = WineSecondary
                )
            ) {
                Text("Agregar al carrito", style = MaterialTheme.typography.titleMedium)
            }
        } else {
            if (viewModel.step > 0) {
                OutlinedButton(
                    onClick = { viewModel.previousStep() },
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Atrás", style = MaterialTheme.typography.titleMedium)
                }
            }
            Button(
                onClick = { viewModel.nextStep() },
                enabled = viewModel.canGoNext(),
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = WineSecondary
                )
            ) {
                Text("Siguiente", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}
