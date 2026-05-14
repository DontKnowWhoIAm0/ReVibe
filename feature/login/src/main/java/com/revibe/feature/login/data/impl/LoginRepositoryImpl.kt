package com.revibe.feature.login.data.impl

import com.revibe.feature.login.data.LoginApiService
import com.revibe.feature.login.data.LoginRepository
import com.revibe.feature.login.domain.model.LoginRequest
import com.revibe.feature.login.domain.model.LoginResponse
import org.json.JSONObject
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: LoginApiService
) : LoginRepository {
    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = apiService.login(request)

        if (response.isSuccessful) {
            return response.body()!!
        }

        val errorBody = response.errorBody()?.string()
        val errorMessage = try {
            JSONObject(errorBody ?: "").getString("error")
        } catch (e: Exception) {
            "Ошибка ${response.code()}"
        }

        throw Exception(errorMessage)
    }
}