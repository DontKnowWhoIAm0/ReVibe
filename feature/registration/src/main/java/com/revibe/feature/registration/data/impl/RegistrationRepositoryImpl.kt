package com.revibe.feature.registration.data.impl

import com.revibe.feature.registration.data.RegistrationApiService
import com.revibe.feature.registration.data.RegistrationRepository
import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.model.RegisterResponse
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: RegistrationApiService
) : RegistrationRepository {
    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return apiService.register(request)
    }
}