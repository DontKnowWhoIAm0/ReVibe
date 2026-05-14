package com.revibe.feature.login.domain.usecase

import com.revibe.feature.login.data.LoginRepository
import com.revibe.feature.login.domain.model.LoginRequest
import com.revibe.feature.login.domain.model.LoginResponse
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(request: LoginRequest): LoginResponse =
        repository.login(request)
}