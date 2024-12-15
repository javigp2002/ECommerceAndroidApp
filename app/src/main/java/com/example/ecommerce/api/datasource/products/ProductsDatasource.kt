package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.domain.repository.model.AddProductModel
import com.example.ecommerce.domain.repository.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductsDatasource {

    @GET("api/products/all")
    suspend fun getAllProducts(): MutableList<Product>?


    @POST("/api/products/add")
    suspend fun addProduct(@Body product: AddProductModel): String

    @POST("/api/cart/buy")
    suspend fun buyCartProducts(cart: MutableList<Product>)
}