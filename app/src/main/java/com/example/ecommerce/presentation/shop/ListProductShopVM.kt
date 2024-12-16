package com.example.ecommerce.presentation.shop

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.launch

class ListProductShopVm(private val productsRepository: ProductsRepository) : ViewModel() {

    val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    init {
        viewModelScope.launch {
            products.setValue(productsRepository.getProducts())
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            products.setValue(productsRepository.getProducts().toList())
        }
    }

    fun manageProduct(product: Product) {
        viewModelScope.launch {
            if (isProductOnCart(product.id)) {
                productsRepository.removeProductFromCart(product)
            } else {
                productsRepository.addProductToCart(product)
            }

            updateProducts()
        }

    }

    private fun isProductOnCart(id: Long): Boolean {
        return productsRepository.isProductOnCart(id)
    }

}
