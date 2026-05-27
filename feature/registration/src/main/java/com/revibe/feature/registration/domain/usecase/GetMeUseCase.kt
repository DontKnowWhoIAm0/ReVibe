package com.revibe.feature.registration.domain.usecase

import com.revibe.feature.registration.data.RegistrationApiService
import com.revibe.feature.registration.domain.model.MeResponse
import javax.inject.Inject

class GetMeUseCase @Inject constructor(
    private val apiService: RegistrationApiService
) {
    suspend operator fun invoke(): MeResponse {
        val response = apiService.getMe()
        if (response.isSuccessful) return response.body()!!
        throw Exception("Ошибка ${response.code()}")
    }
}