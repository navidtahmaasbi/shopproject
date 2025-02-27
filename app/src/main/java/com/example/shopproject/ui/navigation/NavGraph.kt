package com.example.shopproject.ui.navigation

import ProductList
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.shopproject.ui.screens.ProductDetailScreen
import com.example.shopproject.ui.viewmodels.ProductViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.ui.screens.CartScreen
import com.example.shopproject.ui.screens.HomeScreen
import com.example.shopproject.ui.screens.ProfileScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            HomeScreen(
                productViewModel = productViewModel,
                cartViewModel = cartViewModel,
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )
        }
        composable("cart") { CartScreen(cartViewModel) }
        composable("profile") { ProfileScreen() }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {
                val product by productViewModel.getProductById(productId).observeAsState()
                product?.let {
                    ProductDetailScreen(
                        productId = productId,
                        productViewModel = productViewModel,
                        onBackClick = { navController.popBackStack() }
                    )
                } ?: Text("Product not found")
            } else {
                Text("Invalid product ID")
            }
        }
    }
}


