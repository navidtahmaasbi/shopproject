package com.example.shopproject.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.shopproject.R
import com.google.firebase.auth.FirebaseAuth

@Composable
fun BottomNavigationBar(navController: NavController, isAdmin: Boolean) {
    val auth = FirebaseAuth.getInstance()
    val isLoggedIn = auth.currentUser != null

    val items = listOf(
        NavigationItem.Home to Icons.Default.Home,
        NavigationItem.Cart to Icons.Default.ShoppingCart,
        NavigationItem.Profile to Icons.Default.Person
    )
    val adminItem = NavigationItem.Admin to Icons.Default.Settings

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navController.currentDestination?.route

        items.forEach { (item, icon) ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(id = item.title)
                    )
                },
                label = { Text(stringResource(id = item.title)) },
                selected = currentRoute == item.route ||(item == NavigationItem.Profile && currentRoute == "login" && !isLoggedIn),
                onClick = {
                    val route = if (item == NavigationItem.Profile && !isLoggedIn) "login" else item.route
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        if (isAdmin) {
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(id = R.string.admin)
                    )
                },
                label = { Text(stringResource(id = adminItem.first.title)) },
                selected = currentRoute == "admin",
                onClick = {
                    navController.navigate("admin") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }

            )
        }

    }
}
