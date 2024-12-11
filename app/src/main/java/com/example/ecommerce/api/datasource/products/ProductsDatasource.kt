package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.model.AddProductModel
import com.example.ecommerce.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsDatasource {

    @GET("api/products/all")
    suspend fun getAllProducts(): MutableList<Product>?


    @POST("/api/products/add")
    suspend fun addProduct(@Body product: AddProductModel): String
}