package com.example.ecommerce.presentation

import android.os.Bundle
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

class ListProductShopFragment private constructor() : Fragment() {
    private lateinit var viewModel: ListProductShopVm
    private val productAdapter = ProductAdapter()
    private lateinit var recyclerView: RecyclerView


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

        viewModel = AppContainerImpl.listProductShopVm

        recyclerView.layoutManager = GridLayoutManager(this.context, 1)
        recyclerView.adapter = productAdapter
        subscribeToViewModel()

        productAdapter.onProductClicked = {
            viewModel.manageProduct(it)
        }
    }

    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            recyclerView.adapter = productAdapter
            productAdapter.submitList(products.toList())
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
