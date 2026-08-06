package com.revibe.feature.login.domain.usecase

import com.revibe.feature.login.data.LoginApiService
import com.revibe.feature.login.data.dto.UserDto
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val api: LoginApiService
) {
    suspend operator fun invoke(): UserDto {
        val response = api.me()
        if (response.isSuccessful) {
            return response.body()!!
        }
        throw Exception("Ошибка получения профиля")
    }
}