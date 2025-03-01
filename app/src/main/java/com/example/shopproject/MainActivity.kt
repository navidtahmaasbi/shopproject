package com.example.shopproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.ui.navigation.BottomNavigationBar
import com.example.shopproject.ui.navigation.NavGraph
import com.example.shopproject.ui.theme.ShopProjectTheme
import com.example.shopproject.ui.viewmodels.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MainActivity : ComponentActivity() {
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShopProjectTheme {
                AppContent(productViewModel, cartViewModel)

            }
        }
    }
}


@Composable
fun AppContent(productViewModel: ProductViewModel, cartViewModel: CartViewModel) {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    var isAdmin by remember { mutableStateOf(false) }

    LaunchedEffect(auth.currentUser) {
        val user = auth.currentUser
        if (user != null) {
            val doc = FirebaseFirestore.getInstance()
                .collection("users")
                .document(user.uid)
                .get()
                .await()
            isAdmin = doc.getBoolean("isAdmin") ?: false
        } else {
            isAdmin = false
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, isAdmin)
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            productViewModel = productViewModel,
            cartViewModel = cartViewModel,
            isAdmin = isAdmin,
            modifier = Modifier.padding(innerPadding)

        )
    }
}




