package com.revibe.feature.registration.data.impl

import com.revibe.feature.registration.data.RegistrationApiService
import com.revibe.feature.registration.data.RegistrationRepository
import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.model.RegisterResponse
import org.json.JSONObject
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: RegistrationApiService
) : RegistrationRepository {
    override suspend fun register(request: RegisterRequest): RegisterResponse {
        val response = apiService.register(request)

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