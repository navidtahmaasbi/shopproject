package com.example.shopproject

import ProductList
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.data.Product
import com.example.shopproject.ui.navigation.BottomNavigationBar
import com.example.shopproject.ui.screens.CartScreen
import com.example.shopproject.ui.screens.HomeScreen
import com.example.shopproject.ui.screens.ProfileScreen
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
                    bottomBar = {BottomNavigationBar(navController)})
                { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("home"){HomeScreen(productViewModel, cartViewModel)}
                        composable("cart"){CartScreen(cartViewModel)}
                        composable("profile"){ProfileScreen()}
                    }

                }
            }
        }
    }
}



@Composable
fun ProductItem(product: Product, cartViewModel: CartViewModel,onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            val painter: Painter = if (product.imageUrl.isNullOrEmpty()) {
                painterResource(id = R.drawable.placeholder)
            } else {
                painterResource(id = R.drawable.placeholder)

//                    rememberAsyncImagePainter(product.imageUrl)
            }
            Image(
                painter = painter,
                contentDescription = product.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { cartViewModel.addToCart(product) }) {
                Text("Add to cart")
            }
        }

    }
}
