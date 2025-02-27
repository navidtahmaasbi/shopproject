package com.example.shopproject.ui.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopproject.data.Product


class ProductViewModel : ViewModel() {
    private val _allproducts = MutableLiveData<List<Product>>(emptyList())
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

    fun addProduct(product: Product){
        _allproducts.value = _allproducts.value?.plus(product)
    }
    fun editProduct(updatedProduct: Product){
        _allproducts.value = _allproducts.value?.map{ if (it.id == updatedProduct.id) updatedProduct else it}
    }
    fun deleteProduct(productId: String){
        _allproducts.value = _allproducts.value?.filter{it.id != productId}
    }
}



