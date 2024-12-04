package com.example.ecommerce.domain.repository

import com.example.ecommerce.model.Product

interface ProductsRepository {

    suspend fun getProducts(): List<Product>
}
