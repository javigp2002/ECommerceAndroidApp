package com.example.ecommerce.domain.repository

import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepositoryImpl(private val api: ProductsDatasource) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {

        val products = withContext(Dispatchers.IO) {
            try {
                api.getAllProducts()
            } catch (e: Exception) {
                emptyList()
            }
        }
        return products ?: emptyList()
    }

}
