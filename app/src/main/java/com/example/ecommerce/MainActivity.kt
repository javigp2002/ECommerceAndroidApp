package com.example.ecommerce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.model.ProductAdapter
import com.example.ecommerce.presentation.MainActivityViewModel


class MainActivity : ComponentActivity() {
    private lateinit var productAdapter: ProductAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: MainActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = AppContainerImpl()
        viewModel = appContainer.mainActivityViewModel

        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerViewProducts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        subscribeToViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts()
    }

    private fun subscribeToViewModel() {
        viewModel.products.observe(this, {
            productAdapter = ProductAdapter(it)
            recyclerView.adapter = productAdapter
        })
    }

}
