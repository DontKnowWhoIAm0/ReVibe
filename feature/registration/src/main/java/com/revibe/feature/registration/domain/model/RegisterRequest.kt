package com.revibe.feature.registration.domain.model

data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)

