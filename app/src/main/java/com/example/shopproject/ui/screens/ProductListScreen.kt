import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shopproject.R
import com.example.shopproject.data.CartViewModel
import com.example.shopproject.data.Product
import com.example.shopproject.ui.screens.CategoryFilter
import com.example.shopproject.ui.viewmodels.ProductViewModel

@Composable
fun ProductList(
    productViewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier,
    onProductClick: (String) -> Unit
) {
    var isSearching by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = listOf("All", "Electronics", "Clothing", "Books")
    val products by productViewModel.allProducts.observeAsState(emptyList())

    val filteredProducts = products.filter { product ->
        (searchQuery.isEmpty() || product.name.contains(searchQuery, ignoreCase = true)) &&
                (selectedCategory == "All" || product.category == selectedCategory)
    }

    Column(modifier = modifier.fillMaxSize()) {
        // 🔍 Debugging - Confirm this is running

        // ✅ Search Bar (Already visible for you)
        ShopAppBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            isSearching = isSearching,
            onSearchToggle = { isSearching = it }
        )

        // ✅ Add Category Filter - Must be ABOVE LazyColumn
        CategoryFilter(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        // ✅ Product List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(filteredProducts) { product ->
                ProductItem(
                    product = product,
                    cartViewModel = cartViewModel,
                    onClick = { onProductClick(product.id) }
                )
            }
        }
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

@Composable
fun ProductItem(product: Product, cartViewModel: CartViewModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{onClick()},
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            val painter: Painter = if (product.imageUrl.isNullOrEmpty()) {
                painterResource(id = R.drawable.placeholder)
            } else {
                painterResource(id = R.drawable.placeholder)

//                    rememberAsyncImagePainter(product.imageUrl)
            }
            Image(
                painter = painter,
                contentDescription = product.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = product.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = product.description, style = MaterialTheme.typography.bodyMedium)
            }
            Button(onClick = { cartViewModel.addToCart(product) }) {
                Text("Add to cart")
            }
        }

    }
}