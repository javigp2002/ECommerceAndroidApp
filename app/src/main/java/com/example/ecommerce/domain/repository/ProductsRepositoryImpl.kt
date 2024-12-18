package com.example.ecommerce.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.MyApplication
import com.example.ecommerce.api.datasource.products.ProductsDatasource
import com.example.ecommerce.domain.repository.model.AddProductModel
import com.example.ecommerce.domain.repository.model.Product
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductsRepositoryImpl(private val api: ProductsDatasource) : ProductsRepository {
    private var sharedPreferences: SharedPreferences =
        MyApplication.instance.getSharedPreferences("cart", Context.MODE_PRIVATE)

    private var cart: List<Product> = listOf()

    init {
        val cart = sharedPreferences.getString("cart", "")
        if (!cart.isNullOrEmpty()) {
            this.cart = (Gson().fromJson(cart, Array<Product>::class.java))?.toList() ?: listOf()
        }
    }


    override suspend fun getProducts(): List<Product> {
        val products = withContext(Dispatchers.IO) {
            try {
                api.getAllProducts()
                    ?.map {
                        it.copy(onCart = isProductOnCart(it.id))
                    }?.toList()
            } catch (e: Exception) {
                listOf()
            }
        }

        return products ?: listOf()
    }

    override suspend fun getCartProducts(): List<Product> {
        return cart
    }


    override suspend fun addProductToList(product: AddProductModel) {
        withContext(Dispatchers.IO) {
            try {
                api.addProduct(product, getTokenString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override suspend fun addProductToCart(product: Product) {
        withContext(Dispatchers.IO) {
            try {
                api.addProductToCart(product.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        cart = cart + product.copy(onCart = true)
        val cartJson = Gson().toJson(cart)

        sharedPreferences.edit().putString("cart", cartJson).apply()
    }

    override suspend fun cleanCart() {
        withContext(Dispatchers.IO) {
            try {
                api.cleanCart()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun isProductOnCart(productId: Long): Boolean {
        return cart.any { it.id == productId }
    }

    override suspend fun removeProductFromCart(product: Product) {
        withContext(Dispatchers.IO) {
            try {
                api.deleteCartProduct(product.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        cart = cart.filter { it.id != product.id }
        val cartJson = Gson().toJson(cart)
        sharedPreferences.edit().putString("cart", cartJson).apply()
    }

    override suspend fun buyCartProducts() {
        withContext(Dispatchers.IO) {
            try {
                api.buyCartProducts()

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        cart = listOf()
        sharedPreferences.edit().putString("cart", "").apply()

    }

    override suspend fun deleteProduct(it: Product) {
        withContext(Dispatchers.IO) {
            try {
                api.deleteProduct(it.id, getTokenString())
                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    override suspend fun editProduct(product: Product) {
        withContext(Dispatchers.IO) {
            try {
                api.editProduct(
                    product.id,
                    AddProductModel(product.name, product.price),
                    getTokenString()
                )

                return@withContext true
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext false
            }
        }
    }

    private fun getTokenString(): String {
        return "Bearer ${sharedPreferences.getString("token", "")}"
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
