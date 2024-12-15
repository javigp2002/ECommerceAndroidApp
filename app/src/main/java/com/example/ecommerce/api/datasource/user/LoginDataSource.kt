package com.example.ecommerce.api.datasource.user

import com.example.ecommerce.domain.repository.model.LoggedInUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface LoginDataSource {
    @FormUrlEncoded
    @POST("/api/auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoggedInUser
}
