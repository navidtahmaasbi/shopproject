package com.example.shopproject.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ProductDao {
    private val productList = mutableListOf<Product>()
    private val productsLiveData = MutableLiveData<List<Product>>(productList)

    fun insert(product: Product) {
        productList.add(product)
        productsLiveData.value = productList
    }

    fun getAllProducts(): LiveData<List<Product>> {
        return productsLiveData

    }

    fun getProductById(productId: String): LiveData<Product?> {
        val product = productList.find {it.id == productId}
        return MutableLiveData(product)

    }
    fun deleteProductById(productId: String){
        val iterator = productList.iterator()
        while (iterator.hasNext()){
            val product = iterator.next()
            if (product.id == productId){
                iterator.remove()
            }
        }
        productsLiveData.value = productList

    }

    fun updateProduct(productId: String, name: String, description: String, isFree: Boolean){
        val index = productList.indexOfFirst {it.id == productId}
        if (index != -1){
            productList[index] = productList[index].copy(name, description = description, isFree = isFree)
            productsLiveData.value = productList
        }
    }
}