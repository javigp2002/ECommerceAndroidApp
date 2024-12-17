package com.example.ecommerce.presentation.shop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.ProductAdapter
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.domain.repository.model.Product
import com.example.ecommerce.presentation.map.MapsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListProductShopFragment private constructor() : Fragment() {
    private lateinit var viewModel: ListProductShopVm
    private val productAdapter = ProductAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatingButton: FloatingActionButton



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.list_products_shop_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        floatingButton = view.findViewById(R.id.floatingActionButton)

        viewModel = AppContainerImpl.listProductShopVm

        recyclerView.layoutManager = GridLayoutManager(this.context, 1)
        recyclerView.adapter = productAdapter
        subscribeToViewModel()

        productAdapter.onProductClicked = {
            viewModel.manageProduct(it)
        }

        floatingButton.setOnClickListener {
            val intent = Intent(this.context, MapsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            Log.d("Javi", "${products}")
            productAdapter.submitList(products)
        }

        viewModel.products.observe(viewLifecycleOwner, products)
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts()
    }

    companion object {
        fun newInstance(): Fragment {
            return ListProductShopFragment()
        }
    }
}
