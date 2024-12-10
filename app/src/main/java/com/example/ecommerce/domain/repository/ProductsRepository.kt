package com.example.ecommerce.domain.repository

import com.example.ecommerce.model.Product

interface ProductsRepository {

    suspend fun getProducts(): MutableList<Product>
    suspend fun addProductToCart(product: Product)
    fun isProductOnCart(productId: Long): Boolean
    suspend fun removeProductFromCart(product: Product)
}
