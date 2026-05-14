package com.revibe.feature.login.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)