package com.example.ecommerce.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.model.Product
import kotlinx.coroutines.launch

enum class CartCheckoutAction {
    BUY,
    CANCEL
}

class CartCheckoutVM(private val productsRepository: ProductsRepository) : ViewModel() {
    private val actualCartProductsList: MutableList<Product> = mutableListOf()
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
            actualCartProductsList.addAll(productsRepository.getCartProducts())
            updateValueProduct()
            productsLiveData.setValue(actualCartProductsList.toList())
            totalPriceLiveData.setValue(totalValueProducts)
        }
    }


    fun buyProducts() {
        viewModelScope.launch {
            productsRepository.buyCartProducts()
            actionLiveData.setValue(CartCheckoutAction.BUY)
        }
    }

    fun cancelPurchase() {
        viewModelScope.launch {
            actionLiveData.setValue(CartCheckoutAction.CANCEL)
        }
    }


    private fun updateValueProduct() {
        totalValueProducts = 0.0
        actualCartProductsList.forEach {
            totalValueProducts += it.price
        }
    }

}
