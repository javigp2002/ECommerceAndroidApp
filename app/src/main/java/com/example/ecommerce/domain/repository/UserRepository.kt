package com.example.ecommerce.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

class UserRepository {
    private var sharedPreferences: SharedPreferences =
        MyApplication.instance.getSharedPreferences("jwt", Context.MODE_PRIVATE)

    val isUserLoggedIn = MutableSharedFlow<Boolean>()

    suspend fun updateUserLoggedInStatus(isLoggedIn: Boolean) {
        withContext(Dispatchers.IO) {
            isUserLoggedIn.emit(isLoggedIn)
        }
    }

    suspend fun saveUserJwt(jwt: String) {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().putString("jwt", jwt).apply()
            updateUserLoggedInStatus(true)
        }
    }

    suspend fun clearUserJwt() {
        withContext(Dispatchers.IO) {
            sharedPreferences.edit().putString("jwt", "").apply()
            updateUserLoggedInStatus(false)
        }
    }
}