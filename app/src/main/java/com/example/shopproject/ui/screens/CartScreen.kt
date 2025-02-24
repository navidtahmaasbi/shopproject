package com.example.shopproject.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.example.shopproject.data.CartItem
import com.example.shopproject.data.CartViewModel
import androidx.compose.ui.Modifier


@Composable
fun CartScreen(cartViewModel: CartViewModel, modifier: Modifier = Modifier) {
    val cartItems by cartViewModel.cartItems.collectAsState()

    Column(modifier = modifier.fillMaxSize().padding(16.dp)){
        Text(
            text = "Your Cart",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)){
            items(cartItems){cartItem ->
                CartItemRow(cartItem, cartViewModel)
            }
        }
        Button(
            onClick = {/* Navigate to checkout*/},
            modifier = Modifier.fillMaxWidth()
        ){
            Text("Proceed to checkout")
        }

    }
}
@Composable
fun CartItemRow(cartItem: CartItem, cartViewModel: CartViewModel){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = cartItem.product.name, modifier = Modifier.weight(1f))

        Text(text = "Qty: ${cartItem.quantity}")

        Row{
            Button(onClick = { cartViewModel.removeFromCart(cartItem.product) }) {
                Text("-")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = { cartViewModel.addToCart(cartItem.product) }) {
                Text("+")
            }
        }
    }
}