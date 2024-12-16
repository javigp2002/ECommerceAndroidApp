package com.example.ecommerce.presentation.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.launch

class CartShopVm(private val productsRepository: ProductsRepository) : ViewModel() {
    private val actualProducts: MutableList<Product> = mutableListOf()
    private var totalValueProducts: Double = 0.0


    val products: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    val valueObserver: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    init {
        viewModelScope.launch {
            actualProducts.addAll(productsRepository.getCartProducts())

            products.setValue(actualProducts.toList())
            valueObserver.setValue(totalValueProducts)
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            actualProducts.clear()
            actualProducts.addAll(productsRepository.getCartProducts())

            updateValueProduct()

            products.setValue(actualProducts.toList())
            valueObserver.setValue(totalValueProducts)
        }
    }

    fun manageProduct(product: Product) {
        viewModelScope.launch {
            if (isProductOnCart(product.id)) {
                productsRepository.removeProductFromCart(product)
                updateProductsOnCart(product.id, false)
            } else {
                productsRepository.addProductToCart(product)
                updateProductsOnCart(product.id, true)
            }

            updateProducts()
        }

    }

    private fun isProductOnCart(id: Long): Boolean {
        return productsRepository.isProductOnCart(id)
    }

    private fun updateProductsOnCart(productId: Long, isOnCart: Boolean) {
        actualProducts.find { it.id == productId }?.onCart = isOnCart
    }

    private fun updateValueProduct() {
        totalValueProducts = 0.0
        actualProducts.forEach {
            totalValueProducts += it.price
        }
    }

}
