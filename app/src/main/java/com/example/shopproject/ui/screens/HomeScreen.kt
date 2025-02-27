package com.example.shopproject.ui.screens

import ProductList

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.ui.viewmodels.ProductViewModel


@Composable
fun HomeScreen(
    productViewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
        ){
    ProductList(productViewModel, cartViewModel = cartViewModel)
}