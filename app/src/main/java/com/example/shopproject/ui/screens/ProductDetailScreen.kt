package com.example.shopproject.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.shopproject.R
import com.example.shopproject.ui.viewmodels.ProductViewModel

@Composable
fun ProductDetailScreen(
    productId: String,
    productViewModel: ProductViewModel,
    onBackClick: () -> Unit
) {
    Log.d("ProductDetailScreen", "Opened with productId: $productId")
    val product by productViewModel.getProductById(productId).observeAsState()
    Log.d("ProductDetailScreen", "Product data: $product")

    if (product != null){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!product!!.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = product!!.imageUrl,
                    contentDescription = product!!.name,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.placeholder), // Fallback image if no URL
                    contentDescription = product!!.name,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Text(text = product!!.name, style = MaterialTheme.typography.headlineMedium)
            Text(text = product!!.description, style = MaterialTheme.typography.bodyMedium)
            Text(text = "$${product!!.price}", style = MaterialTheme.typography.bodyLarge, color = Color.Green)

            Button(
                onClick = { /* Add to Cart */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to Cart")
            }
            Button(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    } else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ){
            Text("Product not found", color = Color.Red, style = MaterialTheme.typography.headlineMedium)
            Button(onClick = onBackClick){
                Text("Back")
            }
        }
    }
}
