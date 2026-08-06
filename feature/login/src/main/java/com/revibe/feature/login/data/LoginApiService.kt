package com.revibe.feature.login.data

import com.revibe.feature.login.data.dto.UserDto
import com.revibe.feature.login.domain.model.LoginRequest
import com.revibe.feature.login.domain.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("api/auth/me")
    suspend fun me(): Response<UserDto>
}