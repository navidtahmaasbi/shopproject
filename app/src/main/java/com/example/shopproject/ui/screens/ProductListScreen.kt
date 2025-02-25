import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shopproject.ProductItem
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.data.Product
import com.example.shopproject.ui.screens.CategoryFilter
import com.example.shopproject.ui.viewmodels.ProductViewModel

@Composable
fun ProductList(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All","Electronics","Clothing","Books")
    val products by productViewModel.allProducts.observeAsState(emptyList())

    val filteredProducts = products.filter {product ->
    (searchQuery.isEmpty() || product.name.contains(searchQuery, ignoreCase = true)) &&
            (selectedCategory == "All" || product.category == selectedCategory)
    }

    Column {
        ShopAppBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            isSearching = isSearching,
            onSearchToggle = { isSearching = it }
        )

        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(filteredProducts) { product ->
                ProductItem(
                    product = product,
                    cartViewModel = cartViewModel,
                    onClick ={/* Handle item click if needed*/})
            }
        }
        CategoryFilter(
            categories = listOf("All","Electronics","Clothing","Books"),
            selectedCategory = selectedCategory,
            onCategorySelected = { category -> selectedCategory = category }
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopAppBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    isSearching: Boolean,
    onSearchToggle: (Boolean) -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            if (isSearching) {
                TextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = { Text("Search") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()

                )
            } else {
                Text("ShopApp")
            }
        },
        navigationIcon = {
            if (isSearching) {
                IconButton(onClick = { onSearchToggle(false) }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")

                }
            }
        },
        actions = {
            if (!isSearching) {
                IconButton(onClick = { onSearchToggle(true) }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            }
        }
    )
}