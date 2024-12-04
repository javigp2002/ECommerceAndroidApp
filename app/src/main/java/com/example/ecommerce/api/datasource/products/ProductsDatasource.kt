package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.model.Product
import retrofit2.http.GET

interface ProductsDatasource {

    // Get all products
    @GET("api/products/all")
    suspend fun getAllProducts(): List<Product>?


}