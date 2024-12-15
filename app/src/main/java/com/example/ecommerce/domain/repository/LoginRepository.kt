package com.example.ecommerce.domain.repository

import com.example.ecommerce.api.datasource.user.LoginDataSource
import com.example.ecommerce.domain.repository.model.LoggedInUser
import com.example.ecommerce.domain.repository.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return withContext(Dispatchers.IO) {
            val result = dataSource.login(username, password)

            if (result.token.isNotEmpty()) {
                Result.Success(result)
            } else {
                Result.Error(Exception("Invalid credentials"))
            }
        }
    }


}