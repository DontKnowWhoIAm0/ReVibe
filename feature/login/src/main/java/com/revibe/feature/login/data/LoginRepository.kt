package com.revibe.feature.login.data

import com.revibe.feature.login.domain.model.LoginRequest
import com.revibe.feature.login.domain.model.LoginResponse

interface LoginRepository {
    suspend fun login(request: LoginRequest): LoginResponse
}