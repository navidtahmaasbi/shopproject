package com.example.shopproject.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.shopproject.R

sealed class NavigationItem {val route: String, @StringRes val title: Int, @DrawableRes val icon: Int){
    object Home : NavigationItem("home", R.string.home, R.drawable.ic_home)
    object Cart : NavigationItem("cart", R.string.cart, R.drawable.ic_cart)
    object profile : NavigationItem("profile", R.string.profile, R.drawable.ic_profile)

}


