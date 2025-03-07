package com.example.shopproject.data

data class Product (
    val id: String = "",
    val name: String = "نا مشخص",
    val description: String = "بدون توضیحات",
    val isFree: Boolean = false,
    val imageUrl: String? = "https://picsum.photos/300/200",
    val category: String = "",
    val price: Double = 0.0
)
