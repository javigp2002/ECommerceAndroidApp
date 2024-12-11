package com.example.ecommerce.dependency

import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.ProductsRepositoryImpl
import com.example.ecommerce.presentation.CartShopVm
import com.example.ecommerce.presentation.ListProductShopVm

interface AppContainer {
    val productsRepository: ProductsRepository
    val listProductShopVm: ListProductShopVm
    val cartShopVm: CartShopVm

}

class AppContainerImpl : AppContainer {
    private val productDatasource: ProductsDatasource by lazy {
        ApiClient.retrofit.create(ProductsDatasource::class.java)
    }

    override val productsRepository: ProductsRepository by lazy {
        ProductsRepositoryImpl.getInstance(productDatasource)
    }

    override val listProductShopVm: ListProductShopVm by lazy {
        ListProductShopVm(productsRepository)
    }

    override val cartShopVm: CartShopVm by lazy {
        CartShopVm(productsRepository)
    }
}