package com.example.ecommerce.dependency

import com.example.ecommerce.api.ApiClient
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.api.datasource.user.LoginDataSource
import com.example.ecommerce.domain.repository.LoginRepository
import com.example.ecommerce.domain.repository.ProductsRepository
import com.example.ecommerce.domain.repository.ProductsRepositoryImpl
import com.example.ecommerce.domain.repository.UserRepository
import com.example.ecommerce.presentation.admin.edit.AddProductVm
import com.example.ecommerce.presentation.cart.CartShopVm
import com.example.ecommerce.presentation.cart.checkout.CartCheckoutVM
import com.example.ecommerce.presentation.secured.admin.AdminFragmentVm
import com.example.ecommerce.presentation.secured.admin.edit.EditProductVm
import com.example.ecommerce.presentation.secured.login.LoginViewModel
import com.example.ecommerce.presentation.shop.ListProductShopVm

object AppContainerImpl {
    val productDatasource: ProductsDatasource by lazy {
        ApiClient.retrofit.create(ProductsDatasource::class.java)
    }

    val productsRepository: ProductsRepository by lazy {
        ProductsRepositoryImpl.getInstance(productDatasource)
    }

    val listProductShopVm: ListProductShopVm by lazy {
        ListProductShopVm(productsRepository)
    }

    val cartShopVm: CartShopVm by lazy {
        CartShopVm(productsRepository)
    }

    val cartCheckoutVm: CartCheckoutVM by lazy {
        CartCheckoutVM(productsRepository)
    }

    val loginDatasource: LoginDataSource by lazy {
        ApiClient.retrofit.create(LoginDataSource::class.java)
    }

    val loginRepository: LoginRepository by lazy {
        LoginRepository(loginDatasource)
    }

    val userRepository: UserRepository by lazy {
        UserRepository()
    }

    val loginViewModel: LoginViewModel by lazy {
        LoginViewModel(loginRepository, userRepository)
    }

    val adminFragmentVM: AdminFragmentVm by lazy {
        AdminFragmentVm(productsRepository, userRepository)
    }

    val editProductVM: EditProductVm by lazy {
        EditProductVm(productsRepository)
    }

    val addProductVM: AddProductVm by lazy {
        AddProductVm(productsRepository)
    }

}