package com.example.ecommerce.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.MyApplication
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.model.AddProductModel
import com.example.ecommerce.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepositoryImpl(private val api: ProductsDatasource) : ProductsRepository {
    private var sharedPreferences: SharedPreferences =
        MyApplication.instance.getSharedPreferences("cart", Context.MODE_PRIVATE)

    private val cart: MutableList<Product> = mutableListOf()

    init {

        val cart = sharedPreferences.getString("cart", "")
        if (!cart.isNullOrEmpty()) {
            this.cart.addAll(Gson().fromJson(cart, Array<Product>::class.java).toMutableList())
        }
    }


    override suspend fun getProducts(): MutableList<Product> {
        val products = withContext(Dispatchers.IO) {
            try {
                api.getAllProducts()
            } catch (e: Exception) {
                mutableListOf()
            }
        }
        return products ?: mutableListOf()
    }

    override suspend fun getCartProducts(): MutableList<Product> {
        return cart
    }


    override suspend fun addProductToList(product: AddProductModel) {
        withContext(Dispatchers.IO) {
            try {
                api.addProduct(product)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override suspend fun addProductToCart(product: Product) {
        cart.add(product)
        val cartJson = Gson().toJson(cart)
        sharedPreferences.edit().putString("cart", cartJson).apply()
    }

    override fun isProductOnCart(productId: Long): Boolean {
        return cart.any { it.id == productId }
    }

    override suspend fun removeProductFromCart(product: Product) {
        cart.remove(product)
        val cartJson = Gson().toJson(cart)
        sharedPreferences.edit().putString("cart", cartJson).apply()
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductsRepositoryImpl? = null

        fun getInstance(api: ProductsDatasource): ProductsRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ProductsRepositoryImpl(api).also { INSTANCE = it }
            }
        }
    }
}
