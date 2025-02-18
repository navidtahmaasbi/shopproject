package com.example.shopproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopproject.data.AppDatabase
import com.example.shopproject.data.Product
import com.example.shopproject.data.ProductDao
import com.example.shopproject.repository.ProductRepository

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository
    val allProducts: LiveData<List<Product>>

    init {
        val productDao = AppDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        allProducts = repository.allProducts
    }
    fun getProductById(productId: String) : Product?{
        return allProducts.value?.find{it.id.toString() == productId}
    }
    fun insert(product: Product) = viewModelScope.launch {
        repository.insert(product)
    }
}