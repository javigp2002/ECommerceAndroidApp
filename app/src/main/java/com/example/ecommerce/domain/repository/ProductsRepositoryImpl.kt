package com.example.ecommerce.domain.repository

import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.model.Product

class ProductsRepositoryImpl(private val api: ProductsDatasource) : ProductsRepository {

    override suspend fun getProducts(): List<Product> {

        return api.getAllProducts() ?: emptyList()
    }

}
