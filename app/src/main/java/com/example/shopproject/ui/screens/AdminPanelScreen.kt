package com.example.shopproject.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shopproject.data.Product
import com.example.shopproject.ui.viewmodels.ProductViewModel
import java.util.UUID

@Composable
fun AdminPanelScreen(
    productViewModel: ProductViewModel,
    onProductClick: (Product) -> Unit
) {
    val productList by productViewModel.allProducts.observeAsState(emptyList())
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing){
        ProductFormScreen(
            product = selectedProduct,
            productViewModel = productViewModel,
            onFormSubmit = { isEditing = false }
        )
    } else {


    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        Text(
            text = "Admin Panel",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(productList) { product ->
                ProductAdminItem(
                    product = product,
                    onEdit = {
                        selectedProduct = product
                        isEditing = true
                    },
                    onDelete = { productViewModel.deleteProduct(product.id) }
                )
            }
        }

        Button(
            onClick = {
                selectedProduct = null
                isEditing = true
            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text("Add New Product")
        }
    }
}
}


@Composable
fun ProductAdminItem(
    product: Product,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
){
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(onClick = onEdit){
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete){
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
        }
    }
}

@Composable
fun ProductFormScreen(
    product: Product?,
    productViewModel: ProductViewModel,
    onFormSubmit: () -> Unit
){
    var name by remember {mutableStateOf(product?.name?: "")}
    var description by remember {mutableStateOf(product?.description?: "")}
    var price by remember {mutableStateOf(product?.price?.toString()?: "")}
    var imageUrl by remember {mutableStateOf(product?.imageUrl?: "")}

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            label = {Text("Product Name")},
            modifier = Modifier.fillMaxWidth()

        )
        OutlinedTextField(
            value = description,
            onValueChange = {description = it},
            label = {Text("Description")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = price,
            onValueChange = {price = it},
            label = {Text("Price")},
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = imageUrl,
            onValueChange = {imageUrl = it},
            label = {Text("Image URL")},
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val productObj = Product(
                    id = product?.id?: UUID.randomUUID().toString(),
                    name = name,
                    description = description,
                    price = price.toDoubleOrNull()?: 0.0,
                    imageUrl = imageUrl

                )
                if (product == null){
                    productViewModel.addProduct(productObj)
                }else{
                    productViewModel.editProduct(productObj)
                }
                onFormSubmit()

            },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ){
            Text(if (product == null) "Add Product" else "Update Product")
        }
    }
}

@Composable
fun AdminNavGraph(productViewModel: ProductViewModel, onBackClick: () -> Unit) {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var isEditing by remember { mutableStateOf(false) }

    if (isEditing) {
        ProductFormScreen(
            product = selectedProduct,
            productViewModel = productViewModel,
            onFormSubmit = { isEditing = false }
        )
    } else {
        AdminPanelScreen(productViewModel) { product ->
            selectedProduct = product
            isEditing = true
        }
    }
}