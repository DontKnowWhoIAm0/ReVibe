package com.revibe.feature.registration.data

import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApiService {
    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): RegisterResponse
}