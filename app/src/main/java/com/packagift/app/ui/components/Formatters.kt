package com.packagift.app.ui.components

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val priceFormat: NumberFormat = NumberFormat.getNumberInstance(Locale("es", "AR"))
private val dateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

fun formatPrice(value: Int): String = "$" + priceFormat.format(value)

fun formatDate(millis: Long): String = dateFormat.format(Date(millis))
