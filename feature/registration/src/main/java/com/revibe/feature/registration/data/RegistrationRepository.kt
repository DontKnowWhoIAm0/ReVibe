package com.revibe.feature.registration.data

import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.model.RegisterResponse

interface RegistrationRepository {
    suspend fun register(request: RegisterRequest): RegisterResponse
}