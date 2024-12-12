package com.example.ecommerce.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.model.Product
import com.example.ecommerce.model.ProductAdapter
import com.example.ecommerce.utils.MyNumberFormat

class CartShopFragment private constructor() : Fragment() {
    private lateinit var viewModel: CartShopVm
    private val productAdapter = ProductAdapter()
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewPrice: TextView
    private lateinit var goCheckoutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.cart_shop_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerViewProducts)
        textViewPrice = view.findViewById(R.id.textViewPrice)
        goCheckoutButton = view.findViewById(R.id.goCheckoutButton)

        val appContainer = AppContainerImpl()

        viewModel = appContainer.cartShopVm

        recyclerView.layoutManager = GridLayoutManager(this.context, 1)
        recyclerView.adapter = productAdapter
        subscribeToViewModel()

        productAdapter.onProductClicked = {
            viewModel.manageProduct(it)
        }

        goCheckoutButton.setOnClickListener {
            val newIntent = Intent(activity, CartCheckout::class.java)
            startActivity(newIntent)
        }
    }

    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            recyclerView.adapter = productAdapter
            productAdapter.submitList(products.toList())
        }

        val price = Observer { totalPrice: Double ->
            val totalPriceText = "Total: ${MyNumberFormat.doubleToStringEuros(totalPrice)}"
            textViewPrice.text = totalPriceText
        }

        viewModel.products.observe(viewLifecycleOwner, products)
        viewModel.valueObserver.observe(viewLifecycleOwner, price)

    }

    override fun onResume() {
        super.onResume()
        viewModel.updateProducts()
    }

    companion object {
        fun newInstance(): Fragment {
            return CartShopFragment()
        }
    }
}
