package com.example.shopproject.network

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}