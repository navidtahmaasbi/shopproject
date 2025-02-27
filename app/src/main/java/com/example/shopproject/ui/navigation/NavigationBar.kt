package com.example.shopproject.ui.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.*

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Home to Icons.Default.Home,
        NavigationItem.Cart to Icons.Default.ShoppingCart,
        NavigationItem.Profile to Icons.Default.Person
    )
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
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = false }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

    }
}
