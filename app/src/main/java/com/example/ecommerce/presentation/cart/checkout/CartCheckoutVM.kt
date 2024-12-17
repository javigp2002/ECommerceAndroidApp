package com.example.ecommerce.presentation.cart.checkout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.model.Product
import kotlinx.coroutines.launch

enum class CartCheckoutAction {
    BUY,
    CANCEL,
    NONE
}

class CartCheckoutVM(private val productsRepository: ProductsRepository) : ViewModel() {
    private var actualCartProductsList: List<Product> = listOf()
    private var totalValueProducts: Double = 0.0

    val productsLiveData: MutableLiveData<List<Product>> by lazy {
        MutableLiveData<List<Product>>()
    }

    val totalPriceLiveData: MutableLiveData<Double> by lazy {
        MutableLiveData<Double>()
    }

    val actionLiveData: MutableLiveData<CartCheckoutAction> by lazy {
        MutableLiveData<CartCheckoutAction>()
    }

    init {
        viewModelScope.launch {
            actualCartProductsList = productsRepository.getCartProducts()
            updateValueProduct()
            productsLiveData.setValue(actualCartProductsList)
            totalPriceLiveData.setValue(totalValueProducts)
        }
    }


    fun buyProducts() {
        viewModelScope.launch {
            productsRepository.buyCartProducts()
            actionLiveData.setValue(CartCheckoutAction.BUY)
            actionLiveData.setValue(CartCheckoutAction.NONE)
        }
    }

    fun cancelPurchase() {
        viewModelScope.launch {
            actionLiveData.setValue(CartCheckoutAction.CANCEL)
            actionLiveData.setValue(CartCheckoutAction.NONE)
        }
    }

    fun updateProducts() {
        viewModelScope.launch {
            actualCartProductsList = productsRepository.getCartProducts()
            updateValueProduct()
            productsLiveData.setValue(actualCartProductsList)
            totalPriceLiveData.setValue(totalValueProducts)
        }
    }


    private fun updateValueProduct() {
        totalValueProducts = 0.0
        actualCartProductsList.forEach {
            totalValueProducts += it.price
        }
    }

    fun cleanCart() {
        viewModelScope.launch {
            productsRepository.cleanCart()

        }
    }

}
