package com.example.ecommerce.dependency

import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.api.datasource.user.LoginDataSource
import com.example.ecommerce.domain.repository.LoginRepository
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.ProductsRepositoryImpl
import com.example.ecommerce.domain.repository.UserRepository
import com.example.ecommerce.presentation.CartShopVm
import com.example.ecommerce.presentation.ListProductShopVm
import com.example.ecommerce.presentation.admin.AdminFragmentVm
import com.example.ecommerce.presentation.checkout.CartCheckoutVM
import com.example.ecommerce.ui.login.LoginViewModel

interface AppContainer {
    val productsRepository: ProductsRepository
    val listProductShopVm: ListProductShopVm
    val loginRepository: LoginRepository
    val loginDatasource: LoginDataSource
    val cartShopVm: CartShopVm
    val cartCheckoutVm: CartCheckoutVM
    val loginViewModel: LoginViewModel
    val userRepository: UserRepository
    val adminFragmentVM: AdminFragmentVm

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

    override val cartCheckoutVm: CartCheckoutVM by lazy {
        CartCheckoutVM(productsRepository)
    }

    override val loginDatasource: LoginDataSource by lazy {
        ApiClient.retrofit.create(LoginDataSource::class.java)
    }

    override val loginRepository: LoginRepository by lazy {
        LoginRepository(loginDatasource)
    }

    override val userRepository: UserRepository by lazy {
        UserRepository()
    }

    override val loginViewModel: LoginViewModel by lazy {
        LoginViewModel(loginRepository, userRepository)
    }

    override val adminFragmentVM: AdminFragmentVm by lazy {
        AdminFragmentVm(productsRepository, userRepository)
    }

}