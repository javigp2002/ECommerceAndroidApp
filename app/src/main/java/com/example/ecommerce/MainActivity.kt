package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductAdapter
import com.example.ecommerce.presentation.MainActivityViewModel


class MainActivity : ComponentActivity() {
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private var products: List<Product> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = AppContainerImpl()
        viewModel = appContainer.mainActivityViewModel
        subscribeToViewModel()

        setContent {
            ProductsApp(products)
        }

        viewModel.updateProducts()
    }


    override fun onResume() {
        super.onResume()
        setContent {
            ProductsApp(products)
        }
    }

    private fun subscribeToViewModel() {
        viewModel.products.observe(this, {
            products = it
            setContent {
                ProductsApp(products)
            }
        })
    }

    @Composable
    fun ProductsApp(products: List<Product>) {
        val layoutDirection = LocalLayoutDirection.current
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(
                    start = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateStartPadding(layoutDirection),
                    end = WindowInsets.safeDrawing
                        .asPaddingValues()
                        .calculateEndPadding(layoutDirection),
                ),
        ) {
            ProductList(productList = products)
        }

    }


    @Composable
    fun ProductList(productList: List<Product>, modifier: Modifier = Modifier) {
        if (productList.isEmpty()) {
            CircularProgressIndicator(
                modifier = Modifier.then(Modifier.size(94.dp)),
            )
        } else
            LazyColumn(modifier = modifier) {
                items(productList) { product ->
                    ProductCard(
                        product = product,
                        modifier = Modifier.padding(16.dp)
                    )

                }

            }
    }


    @Composable
    fun ProductCard(product: Product, modifier: Modifier = Modifier) {
        Card(modifier = modifier) {
            Column {
                Text(
                    text = product.name,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = product.price.toString(),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall
                )

            }
        }

    }

    @Preview
    @Composable
    private fun ProductCardPreview() {
        ProductCard(Product(1, "Product 1", 10.0))
    }

}
