package com.example.ecommerce.api.datasource.products

import com.example.ecommerce.domain.repository.model.AddProductModel
import com.example.ecommerce.domain.repository.model.Product
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    suspend fun buyCartProducts()

    @GET("/api/products/delete/{id}")
    suspend fun deleteProduct(@Path(value = "id") it: Long, @Header("Authorization") token: String)

    @PUT("/api/products/edit/{id}")
    suspend fun editProduct(
        @Path(value = "id") id: Long,
        @Body product: AddProductModel,
        @Header("Authorization") tokenString: String
    )

    @FormUrlEncoded
    @POST("/api/cart/add")
    suspend fun addProductToCart(@Field("id") id: Long)

    @GET("/api/cart/deleteAll")
    suspend fun cleanCart()

    @FormUrlEncoded
    @POST("/api/cart/delete")
    suspend fun deleteCartProduct(@Field("id") id: Long)

}