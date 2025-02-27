package com.example.shopproject.ui.screens

import ProductList

import androidx.compose.runtime.Composable
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.ui.viewmodels.ProductViewModel


@Composable
fun HomeScreen(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onProductClick: (String) -> Unit
        ){
    ProductList(
        productViewModel = productViewModel,
        cartViewModel = cartViewModel,
        onProductClick = onProductClick
    )
}