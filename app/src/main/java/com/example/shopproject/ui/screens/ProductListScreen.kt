package com.example.shopproject.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.shopproject.data.Product
import com.example.shopproject.ui.viewmodels.ProductViewModel

@Composable
fun ProductListScreen(
    viewModel: ProductViewModel,
    onProductclick: (String) -> Unit
) {
val products by viewModel.allproducts.observeAsState(emptyList())

    LazyColumn {
        items(products){product ->
            ProductItem(
                product = product,
                onClick ={onProductclick{product.id.toString()}}
            )
        }
    }
}
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = product.name, style = MaterialTheme.typography.titleLarge)
//            Text(text = "$${product.price}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
