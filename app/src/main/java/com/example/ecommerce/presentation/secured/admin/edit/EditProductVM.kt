package com.example.ecommerce.presentation.secured.admin.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.R
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class EditProductVm(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    val hasBeenEdited = MutableSharedFlow<Boolean>()
    private val _editForm = MutableLiveData<EditFormState>()
    val editForm: LiveData<EditFormState> = _editForm

    fun editProduct(product: Product) {
        viewModelScope.launch {
            productsRepository.editProduct(product)
            hasBeenEdited.emit(true)
        }
    }

    fun loginDataChanged(username: String, price: String) {
        if (username.isBlank()) {
            _editForm.value = EditFormState(nameError = R.string.invalid_product_name)
        } else if (price.toDoubleOrNull() != null && price.toDouble() < 0) {
            _editForm.value = EditFormState(priceError = R.string.invalid_product_price)
        } else {
            _editForm.value = EditFormState(isDataValid = true)
        }
    }


}
