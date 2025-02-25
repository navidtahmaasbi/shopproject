package com.example.shopproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopproject.data.AppDatabase
import com.example.shopproject.data.Product
import com.example.shopproject.data.ProductDao
import com.example.shopproject.network.ApiService
import com.example.shopproject.network.RetrofitClient
import com.example.shopproject.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val _allproducts = MutableLiveData<List<Product>>()
    val allProducts: LiveData<List<Product>> = _allproducts

    init {
        _allproducts.value = listOf(
            Product(id = "1", name = "Laptop", description = "Gaming Laptop", price = 1200.0, imageUrl = "", category = "Electronics"),
            Product(id = "2", name = "T-Shirt", description = "Cotton T-Shirt", price = 20.0, imageUrl = "", category = "Clothing"),
            Product(id = "3", name = "Smartphone", description = "Latest Android Phone", price = 800.0, imageUrl = "", category = "Electronics"),
            Product(id = "4", name = "Book", description = "Science Fiction Novel", price = 15.0, imageUrl = "", category = "Books")
        )
    }
    fun getProductById(productId: String): LiveData<Product?> {
        val result = MutableLiveData<Product?>()
        result.value = _allproducts.value?.find { it.id == productId }
        return result
    }
}



