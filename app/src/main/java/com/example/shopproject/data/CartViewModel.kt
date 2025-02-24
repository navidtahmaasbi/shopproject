package com.example.shopproject.data

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
            val cartItems: StateFlow<List<CartItem>> = _cartItems


    fun addToCart(product: Product){
        val currentItems = _cartItems.value.toMutableList()
        val existingItem = currentItems.find {it.product.id == product.id}

        if (existingItem != null){
            existingItem.quantity++
        }else{
            currentItems.add(CartItem(product, 1))
        }
        _cartItems.value = currentItems


        }
    fun removeFromCart(product: Product){
        val currentItems = _cartItems.value.toMutableList()
        val item = currentItems.find {it.product.id == product.id}
        if (item != null){
            if (item.quantity > 1){
                item.quantity--
            }else{
                currentItems.remove(item)

        }
            _cartItems.value = currentItems
    }
    }
    fun clearCart(){
        _cartItems.value = emptyList()

    }
}