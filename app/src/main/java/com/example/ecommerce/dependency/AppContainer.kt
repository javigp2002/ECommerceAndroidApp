package com.example.ecommerce.dependency

import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.ProductsRepositoryImpl
import com.example.ecommerce.presentation.MainActivityViewModel

interface AppContainer {
    val productsRepository: ProductsRepository
    val mainActivityViewModel: MainActivityViewModel

}

class AppContainerImpl : AppContainer {
    private val productDatasource: ProductsDatasource by lazy {
        ApiClient.retrofit.create(ProductsDatasource::class.java)
    }

    override val productsRepository: ProductsRepository by lazy {
        ProductsRepositoryImpl(productDatasource)
    }

    override val mainActivityViewModel: MainActivityViewModel by lazy {
        MainActivityViewModel(productsRepository)
    }
}