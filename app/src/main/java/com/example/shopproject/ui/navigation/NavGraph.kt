package com.example.shopproject.ui.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.ui.screens.ProductDetailScreen
import com.example.shopproject.ui.screens.ProductListScreen
import com.example.shopproject.ui.viewmodels.ProductViewModel

@Composable
fun NavGraph(viewModel: ProductViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "productList"
    ) {

        composable("productList") {
            ProductListScreen(
                viewModel = viewModel,
                onProductClick = { productId ->
                    navController.navigate("productDetail/$productId")
                }
            )


        }
        composable("productDetail/{productId}"){backStackEntry ->

            val productId = backStackEntry.arguments?.getString("productId")
            if (productId != null) {
                val product = viewModel.getProductById(productId)
                if (product != null){
                    ProductDetailScreen(
                        product = product,
                        onBackClick = {
                            navController.popBackStack()
                        }
                    )
                }else{
                    Text("product not found")
                }
            }else{
                Text("Invalid product ID")
            }
        }
    }
}
