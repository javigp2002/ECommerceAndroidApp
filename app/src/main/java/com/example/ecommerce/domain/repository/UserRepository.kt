package com.example.ecommerce.domain.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.MyApplication

class UserRepository {
    private var sharedPreferences: SharedPreferences =
        MyApplication.instance.getSharedPreferences("jwt", Context.MODE_PRIVATE)


    fun getUserJwt(): String {
        return sharedPreferences.getString("jwt", "") ?: ""
    }

    fun saveUserJwt(jwt: String) {
        sharedPreferences.edit().putString("jwt", jwt).apply()
    }

    fun clearUserJwt() {
        sharedPreferences.edit().putString("jwt", "").apply()
    }

}