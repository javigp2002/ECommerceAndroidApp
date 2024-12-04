package com.example.ecommerce.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.model.Product
import kotlinx.coroutines.launch

class MainActivityViewModel(private val productsRepository: ProductsRepository) : ViewModel() {
    val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    init {
        viewModelScope.launch {
            val productsItems = productsRepository.getProducts()
            products.setValue(productsItems)
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            products.setValue(productsRepository.getProducts())
        }
    }
}