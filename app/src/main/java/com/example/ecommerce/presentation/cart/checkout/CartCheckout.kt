package com.example.ecommerce.presentation.cart.checkout

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.adapters.ProductCheckoutAdapter
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.domain.repository.model.Product
import com.example.ecommerce.utils.MyNumberFormat


class CartCheckout : FragmentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var priceText: TextView
    private lateinit var buyButton: Button
    private lateinit var cancelButton: Button

    private lateinit var viewModel: CartCheckoutVM
    private val productAdapter = ProductCheckoutAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.checkout_fragment)
        recyclerView = findViewById(R.id.recyclerViewProducts)
        priceText = findViewById(R.id.totalPrice)
        buyButton = findViewById(R.id.buyButton)
        cancelButton = findViewById(R.id.cancelButton)

        recyclerView.layoutManager = GridLayoutManager(this, 1)
        recyclerView.adapter = productAdapter

        viewModel = AppContainerImpl.cartCheckoutVm
        subscribeToViewModel()


        buyButton.setOnClickListener {
            viewModel.buyProducts()
        }

        cancelButton.setOnClickListener {
            viewModel.cancelPurchase()
        }
    }


    private fun subscribeToViewModel() {
        val products = Observer { products: List<Product> ->
            recyclerView.adapter = productAdapter
            productAdapter.submitList(products.toList())
        }

        val price = Observer { totalPrice: Double ->

            val totalPriceText = "Total: ${MyNumberFormat.doubleToStringEuros(totalPrice)}"
            priceText.text = totalPriceText
        }

        val action = Observer { action: CartCheckoutAction ->
            when (action) {
                CartCheckoutAction.BUY -> {
                    okDialogFragment().show(supportFragmentManager, "dialog")
                }

                CartCheckoutAction.CANCEL -> {
                    finish()
                }
            }
        }

        viewModel.productsLiveData.observe(this, products)
        viewModel.totalPriceLiveData.observe(this, price)
        viewModel.actionLiveData.observe(this, action)

    }

}

class okDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Buy completed Correctly")
                .setPositiveButton("OK") { dialog, id ->
                    activity?.finish()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}