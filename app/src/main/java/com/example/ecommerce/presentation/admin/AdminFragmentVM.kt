package com.example.ecommerce.presentation.admin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.UserRepository
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.launch

class AdminFragmentVm(
    private val productsRepository: ProductsRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val actualProducts: MutableList<Product> = mutableListOf()

    val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    init {
        viewModelScope.launch {
            actualProducts.addAll(productsRepository.getProducts())
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            actualProducts.clear()
            actualProducts.addAll(productsRepository.getProducts())

            products.postValue(actualProducts.toList())
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearUserJwt()
        }
    }

    fun deleteProduct(it: Product) {
        viewModelScope.launch {
            productsRepository.deleteProduct(it)
            updateProducts()
        }
    }
}
