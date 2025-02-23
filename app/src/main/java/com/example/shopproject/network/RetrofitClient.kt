package com.example.shopproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val Base_URL = "https://run.mocky.io/v3/"

    internal val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Base_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    internal inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)

    }

}