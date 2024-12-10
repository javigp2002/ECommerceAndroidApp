package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductAdapter
import com.example.ecommerce.presentation.MainActivityViewModel


class MainActivity : ComponentActivity() {
    private val productAdapter = ProductAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val appContainer = AppContainerImpl()
        viewModel = appContainer.mainActivityViewModel


        recyclerView = findViewById(R.id.recyclerViewProducts)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewProducts)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = productAdapter
        subscribeToViewModel()

        productAdapter.onProductClicked = {
            viewModel.manageProduct(it)
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts()
    }

    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            recyclerView.adapter = productAdapter
            productAdapter.submitList(products.toList())
        }

        viewModel.products.observe(this, products)
    }

}
