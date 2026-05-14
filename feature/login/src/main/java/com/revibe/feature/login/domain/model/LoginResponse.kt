package com.revibe.feature.login.domain.model

data class LoginResponse(
    val token: String,
    val userId: String
)