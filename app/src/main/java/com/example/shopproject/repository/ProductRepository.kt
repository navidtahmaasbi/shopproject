package com.example.shopproject.repository

import androidx.lifecycle.LiveData
import com.example.shopproject.data.Product
import com.example.shopproject.data.ProductDao

class ProductRepository(private val productDao: ProductDao) {

    // Get all products
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    // Get a product by ID
    fun getProductById(productId: String): LiveData<Product?> {
        return productDao.getProductById(productId)
    }

    // Insert a product
    suspend fun insert(product: Product) {
        productDao.insert(product)
    }

    // Delete a product
    suspend fun deleteProductById(productId: String) {
        productDao.deleteProductById(productId)
    }

    // Update a product
    suspend fun updateProduct(productId: String, name: String, description: String, isFree: Boolean) {
        productDao.updateProduct(productId, name, description, isFree)
    }
}