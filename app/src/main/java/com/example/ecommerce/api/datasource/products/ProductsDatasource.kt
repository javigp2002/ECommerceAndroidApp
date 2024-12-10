package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.model.Product
import retrofit2.http.GET

interface ProductsDatasource {

    @GET("api/products/all")
    suspend fun getAllProducts(): MutableList<Product>?
}