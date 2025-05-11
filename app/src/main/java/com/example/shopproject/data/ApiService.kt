package com.example.shopproject.data

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}

data class RegisterRequest(
    val email: String,
    val password: String,
    val password_confirmation: String
)

data class RegisterResponse(
    val user: User,
    val token: String
)

data class User(
    val id: String,
    val email: String,
    val isAdmin: Boolean
)
