package com.example.shopproject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shopproject.data.Product

@Composable
fun ProductDetailScreen(
    product: Product?,
    onBackClick: () -> Unit
) {
    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
            Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            Text(
                text = if (product.isFree) "Free" else "Requires Subscription",
                style = MaterialTheme.typography.bodySmall,
                color = if (product.isFree) Color.Green else Color.Red
            )
        }
    }
}
