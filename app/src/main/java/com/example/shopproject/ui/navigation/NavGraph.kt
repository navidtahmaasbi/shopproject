package com.example.shopproject.ui.navigation

import ProductListScreen
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.ui.screens.ProductDetailScreen
import com.example.shopproject.ui.viewmodels.ProductViewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.shopproject.data.Product


@Composable
fun NavGraph(viewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "productList"
    ) {
        // Product List Screen
        composable("productList") {
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    // Navigate to Product Detail Screen with the productId
                    navController.navigate("productDetail/$productId")
                }
            )
        }

        // Product Detail Screen
        composable("productDetail/{productId}") { backStackEntry ->
            // Retrieve the productId from the route arguments
            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {
                // Observe the product details from the ViewModel

                val product by viewModel.getProductById(productId).observeAsState(Product())
                if (product?.id != "0") {
                    ProductDetailScreen(
                        product = product,
                        onBackClick = {
                            // Navigate back to the Product List Screen
                            navController.popBackStack()
                        }
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

