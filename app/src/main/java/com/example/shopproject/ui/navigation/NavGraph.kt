package com.example.shopproject.ui.navigation

import ProductList
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.ui.screens.ProductDetailScreen
import com.example.shopproject.ui.viewmodels.ProductViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.data.Product


@Composable
fun NavGraph(navController: NavHostController, productViewModel: ProductViewModel, cartViewModel: CartViewModel) {
    NavHost(
        navController = navController,
        startDestination = "productList"
    ) {
        // Product List Screen
        composable("productList") {
            ProductList(
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                modifier = Modifier
            )
        }


        // Product Detail Screen
        composable("productDetail/{productId}") { backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {

                val product by productViewModel.getProductById(productId).observeAsState(Product())
                if (product?.id != "0") {
                    ProductDetailScreen(
                        product = product,
                        onBackClick = { navController.popBackStack() }
                    )
                } else {
                    // Handle case where product is not found
                    Text("Product not found")
                }
            } else {
                // Handle case where productId is null
                Text("Invalid product ID")
            }
        }
    }
}

