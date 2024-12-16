package com.example.ecommerce.presentation.secured.admin.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce.R
import com.example.ecommerce.dependency.AppContainerImpl
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.launch

class EditProductActivity : FragmentActivity() {
    private lateinit var viewModel: EditProductVm
    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var editButton: Button
    private lateinit var cancelButton: Button
    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_product_activity)
        productNameEditText = findViewById(R.id.nameEditText)
        productPriceEditText = findViewById(R.id.priceEditText)
        editButton = findViewById(R.id.editButton)
        cancelButton = findViewById(R.id.cancelButton)

        viewModel = AppContainerImpl.editProductVM
        subscribeToViewModel()

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.loginDataChanged(
                    productNameEditText.text.toString(),
                    productPriceEditText.text.toString()
                )
            }
        }

        productNameEditText.addTextChangedListener(afterTextChangedListener)
        productPriceEditText.addTextChangedListener(afterTextChangedListener)

        val productName = intent.extras?.getString("productName")
        val productPrice = intent.extras?.getDouble("productPrice")
        val productId = intent.extras?.getLong("productId")

        if (productName == null || productPrice == null || productId == null) {
            finish()
        } else {

            productNameEditText.setText(productName)
            productPriceEditText.setText(productPrice.toString())
            product = Product(productId, productName, productPrice)
        }



        editButton.setOnClickListener {
            viewModel.editProduct(
                Product(
                    product.id,
                    productNameEditText.text.toString(),
                    productPriceEditText.text.toString().toDouble()
                )
            )
        }

        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun subscribeToViewModel() {
        lifecycleScope.launch {
            viewModel.hasBeenEdited.collect { hasBeenEdited ->
                if (hasBeenEdited) {
                    finish()
                }
            }

        }

        viewModel.editForm.observe(this, Observer {
            val editState = it ?: return@Observer

            if (editState.nameError != null) {
                productNameEditText.error = getString(editState.nameError)
            }
            if (editState.priceError != null) {
                productPriceEditText.error = getString(editState.priceError)
            }

            editButton.isEnabled = editState.isDataValid
        })
    }

}
