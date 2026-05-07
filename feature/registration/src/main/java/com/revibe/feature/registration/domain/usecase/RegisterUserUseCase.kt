package com.revibe.feature.registration.domain.usecase

import com.revibe.feature.registration.data.RegistrationRepository
import com.revibe.feature.registration.domain.model.RegisterRequest
import com.revibe.feature.registration.domain.model.RegisterResponse
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val repository: RegistrationRepository) {

    suspend operator fun invoke(request: RegisterRequest): RegisterResponse {
        return repository.register(request)
    }

}