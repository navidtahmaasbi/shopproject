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
            Product(
                id = "1",
                name = "laptop",
                description = "this is a laptop",
                imageUrl = "https://via.placeholder.com/150"
            ),
            Product(
                id = "2",
                name = "phone",
                description = "this is a phone",
                imageUrl = "https://via.placeholder.com/150"
            )
        )
    }
    fun getProductById(productId: String): LiveData<Product?> {
        val result = MutableLiveData<Product?>()
        result.value = _allproducts.value?.find { it.id == productId }
        return result
    }
}



