package com.example.ecommerce.presentation.admin.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.R
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.model.AddProductModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class AddProductVm(
    private val productsRepository: ProductsRepository,
) : ViewModel() {

    val hasBeenEdited = MutableSharedFlow<Boolean>()
    private val _editForm = MutableLiveData<AddFormState>()
    val editForm: LiveData<AddFormState> = _editForm

    fun addProduct(product: AddProductModel) {
        viewModelScope.launch {
            productsRepository.addProductToList(product)
            hasBeenEdited.emit(true)
        }
    }

    fun addDataChanged(username: String, price: String) {
        if (username.isBlank()) {
            _editForm.value = AddFormState(nameError = R.string.invalid_product_name)
        } else if (price.toDoubleOrNull() != null && price.toDouble() < 0) {
            _editForm.value = AddFormState(priceError = R.string.invalid_product_price)
        } else {
            _editForm.value = AddFormState(isDataValid = true)
        }
    }


}
