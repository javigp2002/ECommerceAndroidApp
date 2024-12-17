package com.example.ecommerce.domain.repository

import com.example.ecommerce.domain.repository.model.AddProductModel
import com.example.ecommerce.domain.repository.model.Product

interface ProductsRepository {

    suspend fun getProducts(): List<Product>
    suspend fun getCartProducts(): List<Product>
    suspend fun addProductToList(product: AddProductModel)
    suspend fun addProductToCart(product: Product)
    fun isProductOnCart(productId: Long): Boolean
    suspend fun removeProductFromCart(product: Product)
    suspend fun buyCartProducts()
    suspend fun deleteProduct(it: Product)
    suspend fun editProduct(product: Product)
    suspend fun cleanCart()
}
