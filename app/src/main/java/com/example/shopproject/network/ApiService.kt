package com.example.shopproject.network

import com.example.shopproject.data.Product
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}