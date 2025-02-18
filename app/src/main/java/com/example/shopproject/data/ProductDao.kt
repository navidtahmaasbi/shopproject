package com.example.shopproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {

    // Insert a product into the database
    @Insert
    suspend fun insert(product: Product)

    // Get all products from the database
    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<Product>>

    // Get a specific product by its ID
    @Query("SELECT * FROM products WHERE id = :productId")
    fun getProductById(productId: String): LiveData<Product?>

    // Delete a product from the database
    @Query("DELETE FROM products WHERE id = :productId")
    suspend fun deleteProductById(productId: String)

    // Update a product in the database
    @Query("UPDATE products SET name = :name, description = :description, isFree = :isFree WHERE id = :productId")
    suspend fun updateProduct(productId: String, name: String, description: String, isFree: Boolean)
}