package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.domain.repository.model.AddProductModel
import com.example.ecommerce.domain.repository.model.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductsDatasource {

    @GET("api/products/all")
    suspend fun getAllProducts(): List<Product>?


    @POST("/api/products/add")
    suspend fun addProduct(
        @Body product: AddProductModel,
        @Header("Authorization") token: String
    ): String

    @POST("/api/cart/buy")
    suspend fun buyCartProducts(cart: List<Product>)

    @GET("/api/products/delete/{id}")
    suspend fun deleteProduct(@Path(value = "id") it: Long, @Header("Authorization") token: String)

    @PUT("/api/products/edit/{id}")
    suspend fun editProduct(
        @Path(value = "id") id: Long,
        @Body product: AddProductModel,
        @Header("Authorization") tokenString: String
    )
}