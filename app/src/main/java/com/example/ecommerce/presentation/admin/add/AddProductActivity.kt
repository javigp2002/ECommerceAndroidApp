package com.example.ecommerce.presentation.admin.edit

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
import com.example.ecommerce.domain.repository.model.AddProductModel
import kotlinx.coroutines.launch

class AddProductActivity : FragmentActivity() {
    private lateinit var viewModel: AddProductVm
    private lateinit var productNameEditText: EditText
    private lateinit var productPriceEditText: EditText
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_product_activity)
        productNameEditText = findViewById(R.id.nameAddEditText)
        productPriceEditText = findViewById(R.id.priceAddEditText)
        addButton = findViewById(R.id.addButton)
        cancelButton = findViewById(R.id.cancelButton)

        viewModel = AppContainerImpl.addProductVM
        subscribeToViewModel()

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                viewModel.addDataChanged(
                    productNameEditText.text.toString(),
                    productPriceEditText.text.toString()
                )
            }
        }

        productNameEditText.addTextChangedListener(afterTextChangedListener)
        productPriceEditText.addTextChangedListener(afterTextChangedListener)

        addButton.setOnClickListener {
            viewModel.addProduct(
                AddProductModel(
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

            addButton.isEnabled = editState.isDataValid
        })
    }

}
