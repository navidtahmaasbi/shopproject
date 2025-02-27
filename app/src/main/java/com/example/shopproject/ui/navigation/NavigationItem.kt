package com.example.shopproject.ui.navigation


import com.example.shopproject.R

sealed class NavigationItem(val route: String, val title: Int){
    object Home: NavigationItem("home", R.string.home)
    object Cart: NavigationItem("cart",R.string.cart)
    object Profile: NavigationItem("profile",R.string.profile)
}


