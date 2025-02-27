package com.example.shopproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.ui.navigation.BottomNavigationBar
import com.example.shopproject.ui.navigation.NavGraph
import com.example.shopproject.ui.theme.ShopProjectTheme
import com.example.shopproject.ui.viewmodels.ProductViewModel

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopProjectTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavGraph(
                        navController = navController,
                        productViewModel = productViewModel,
                        cartViewModel = cartViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

